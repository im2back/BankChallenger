package io.github.im2back.usermicroservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.im2back.usermicroservice.amqp.UserTransferPublish;
import io.github.im2back.usermicroservice.model.dto.TransferResponseDto;
import io.github.im2back.usermicroservice.model.dto.TransferRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.model.dto.UserRegisterResponseDto;
import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.UserNotFoundException;
import io.github.im2back.usermicroservice.service.util.NotificationRequestDto;
import io.github.im2back.usermicroservice.validation.transfer.TransferValidations;
import io.github.im2back.usermicroservice.validation.user.UserRegistrationValidation;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	
	
	@Autowired
	private List<UserRegistrationValidation> userRegistrationValidation;

	@Autowired
	private List<TransferValidations> transferValidations;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserTransferPublish userTransferPublish;

	@Transactional
	public UserRegisterResponseDto saveUser(UserRegisterRequestDto dto) {
		// Validações de cadastro
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
	public void transfer(TransferRequestDto dto) throws JsonProcessingException {

		try {
			// Executando as validações antes de iniciar a transferência
			transferValidations.forEach(valid -> valid.valid(dto.idPayer(), dto.idPayee(), dto.value()));

			// Persistindo a transferência
			User userPayee = persistTransfer(dto);

			// Notificando beneficiario
			notificationService.sendNotification(
					new NotificationRequestDto(userPayee.getEmail(), "PAYMENT RECEIVED: " + dto.value()));

			// Retornando a resposta da transferência
			userTransferPublish.responseTransfer(new TransferResponseDto(dto.idTransaction(), "sucess", true));

		} catch (RuntimeException e) {
			userTransferPublish.responseTransfer(new TransferResponseDto(dto.idTransaction(), e.getMessage(), false));
		}

	}

	private User persistTransfer(TransferRequestDto dto) {
		// Carregando os usuarios envolvidos na transferência
		User userPayer = findById(dto.idPayer());
		User userPayee = findById(dto.idPayee());

		// Transferindo
		userPayer.getWallet().getType().transfer(userPayer, dto.value());
		userPayee.getWallet().receiveTransfer(dto.value());

		// Persistindo a transferência
		repository.saveAll(Arrays.asList(userPayee, userPayer));
		return userPayee;
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

}
