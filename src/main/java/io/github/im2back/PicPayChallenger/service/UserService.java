package io.github.im2back.PicPayChallenger.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.im2back.PicPayChallenger.model.dto.UserRegisterRequestDto;
import io.github.im2back.PicPayChallenger.model.dto.UserRegisterResponseDto;
import io.github.im2back.PicPayChallenger.model.entities.user.User;
import io.github.im2back.PicPayChallenger.model.entities.wallet.Wallet;
import io.github.im2back.PicPayChallenger.repositories.UserRepository;
import io.github.im2back.PicPayChallenger.service.exceptions.UserNotFoundException;
import io.github.im2back.PicPayChallenger.service.util.NotificationRequestDto;
import io.github.im2back.PicPayChallenger.validation.transfer.TransferValidations;
import io.github.im2back.PicPayChallenger.validation.user.UserRegistrationValidation;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private List<UserRegistrationValidation> userRegistrationValidation;

	@Autowired
	private List<TransferValidations> transferValidations;

	@Transactional
	public UserRegisterResponseDto saveUser(UserRegisterRequestDto dto) {
		userRegistrationValidation.forEach(valid -> valid.valid(dto));

		// Criação da Wallet
		Wallet wallet = new Wallet();
		wallet.setBalance(BigDecimal.ZERO);

		// Criação do User e associação da Wallet
		User user = new User(dto.fullName(), dto.identificationDocument(), dto.email(), dto.password(), dto.type(),
				wallet);
		wallet.setUser(user);

		// Persistência do User, o que também deve persistir a Wallet devido ao cascata
		User savedUser = repository.save(user);

		// Construção do DTO de resposta
		return new UserRegisterResponseDto(savedUser.getId(), savedUser.getFullName(),
				savedUser.getIdentificationDocument(), savedUser.getEmail(), savedUser.getPassword(),
				savedUser.getType(), savedUser.getWallet().getId());

	}

	@Transactional
	public void transfer(Long idPayer, Long idPayee, BigDecimal value) {

		// carreguei os usuarios envolvidos
		User userPayer = findById(idPayer);
		User userPayee = findById(idPayee);

		// validacoes
		transferValidations.forEach(valid -> valid.valid(userPayer, userPayee, value));
		
		// autorizador externo
		finalizeTransfer(); 
		
		// transferencia
		receivePayment(userPayer, userPayee, value); 
		
		// persistindo a transferencia
		repository.saveAll(Arrays.asList(userPayee, userPayer)); 
	}

	public void receivePayment(User userPayer, User userPayee, BigDecimal value) {
		userPayer.getWallet().transfer(value);
		userPayee.getWallet().receiveTransfer(value);

		// Enviar notificação
		NotificationRequestDto notificationRequest = new NotificationRequestDto(userPayee.getEmail(),
				"Pagamento recebido com sucesso!");
		notificationService.sendNotification(notificationRequest);
	}

	private void finalizeTransfer() {
		if (authorizationService.authorizeTransfer() == false) {
			throw new RuntimeException("Transfer not authorized");
		}
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
