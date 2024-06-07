package io.github.im2back.usermicroservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterResponseDto;
import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.UserNotFoundException;
import io.github.im2back.usermicroservice.service.util.NotificationRequestDto;
import io.github.im2back.usermicroservice.validation.user.UserRegistrationValidation;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private List<UserRegistrationValidation> userRegistrationValidation;
	
	@Autowired
	private NotificationService notificationService;

	@Transactional
	public UserRegisterResponseDto saveUser(UserRegisterRequestDto dto) {
		userRegistrationValidation.forEach(valid -> valid.valid(dto));

		// Criação do User 
		User user = new User(dto.fullName(), dto.identificationDocument(), dto.email(), dto.password(), dto.type(),
				null);

		// Criação da Wallet e associação
		user.getType().createWallet(user);

		// Persistência do User, o que também deve persistir a Wallet devido ao cascata
		User savedUser = repository.save(user);

		// Construção do DTO de resposta
		return new UserRegisterResponseDto(savedUser);

	}

	@Transactional
	public void transfer(TransferRequestDto dto) {

		// Carreguei os usuarios envolvidos e transferindo
		User userPayer = findById(dto.idPayer());
		User userPayee = findById(dto.idPayee());

		userPayer.getWallet().getType().transfer(userPayer, dto.value());
		userPayee.getWallet().receiveTransfer(dto.value());

		// persistindo a transferencia
		repository.saveAll(Arrays.asList(userPayee, userPayer));
		
		// notificando recebedor 
		notificationService.sendNotification(new NotificationRequestDto(userPayee.getEmail(), "PAYMENT RECEIVED: "+dto.value()));
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public Optional<User> findByDocument(String document) {
		return repository.findByIdentificationDocument(document);
	}

	@Transactional(readOnly = true)
	public Optional<User> findByEmail(String Email) {
		return repository.findByEmail(Email);
	}

}
