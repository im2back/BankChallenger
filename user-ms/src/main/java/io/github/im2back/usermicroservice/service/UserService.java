package io.github.im2back.usermicroservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterResponseDto;
import io.github.im2back.usermicroservice.model.entities.user.UserComum;
import io.github.im2back.usermicroservice.model.entities.user.UserGeneric;
import io.github.im2back.usermicroservice.model.entities.user.UserLogista;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.UserNotFoundException;
import io.github.im2back.usermicroservice.service.utils.NotificationRequestDto;
import io.github.im2back.usermicroservice.validation.user.UserRegistrationValidation;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private List<UserRegistrationValidation> userRegistrationValidation;

	@Autowired
	private NotificationService notificationService;

	@Transactional
	public UserRegisterResponseDto saveUser(UserRegisterRequestDto dto) {
		userRegistrationValidation.forEach(valid -> valid.valid(dto));

		// Chamado da classe Factory para criar o nosso usuário com base no tipo de
		UserGeneric user = UserFactory.createUser(dto.fullName(), dto.email(), dto.password(),
				dto.identificationDocument());

		// Persistência do User, o que também deve persistir a Wallet devido ao cascata
		UserGeneric savedUser = userRepository.save(user);

		// Chamando o método que realiza downcast e monta o retorno
		return downcastForDto(savedUser);
	}

	@Transactional
	public void transfer(TransferRequestDto dto) {
		processTransferPayer(dto.idPayer(), dto.value());
		processTransferReceive(dto.idPayee(), dto.value());
	}

	private void processTransferPayer(Long id, BigDecimal amount) {
		UserGeneric user = findByIdReturnEntity(id);

		if (user instanceof UserComum) {
			UserComum userComum = (UserComum) user;
			userComum.getWallet().transfer(amount);
		} else if (user instanceof UserLogista) {
			UserLogista userLogista = (UserLogista) user;
			throw new RuntimeException("Usuário é logista:" + userLogista.getFullName());
		} else {
			throw new RuntimeException("Unknown user type");
		}
		userRepository.save(user);
	}

	private void processTransferReceive(Long id, BigDecimal amount) {
		UserGeneric user = findByIdReturnEntity(id);

		if (user instanceof UserComum) {
			UserComum userComum = (UserComum) user;
			userComum.getWallet().receiveTransfer(amount);
		} else if (user instanceof UserLogista) {
			UserLogista userLogista = (UserLogista) user;
			userLogista.getWallet().receiveTransfer(amount);
		} else {
			throw new RuntimeException("Unknown user type");
		}
		notificationService
				.sendNotification(new NotificationRequestDto(user.getEmail(), "Pagamento Recebido :" + amount));
		userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public UserRegisterResponseDto findByIdReturnDto(Long id) {
		UserGeneric user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return downcastForDto(user);
	}

	@Transactional(readOnly = true)
	UserGeneric findByIdReturnEntity(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	private UserRegisterResponseDto downcastForDto(UserGeneric user) {
		if (user instanceof UserComum) {
			UserComum userComum = (UserComum) user;

			return new UserRegisterResponseDto(userComum.getId(), userComum.getFullName(),userComum.getPassword(), userComum.getCpf(),
					userComum.getEmail(),  userComum.getWallet().getBalance());

		} else if (user instanceof UserLogista) {
			UserLogista userLogista = (UserLogista) user;

			return new UserRegisterResponseDto(userLogista.getId(), userLogista.getFullName(),userLogista.getPassword(), userLogista.getCnpj(),
					userLogista.getEmail(),  userLogista.getWallet().getBalance());
		} else {
			throw new RuntimeException("downcast fail");
		}

	}

}
