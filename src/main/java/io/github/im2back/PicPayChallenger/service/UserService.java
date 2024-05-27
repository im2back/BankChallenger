package io.github.im2back.PicPayChallenger.service;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.im2back.PicPayChallenger.model.dto.UserRegisterRequestDto;
import io.github.im2back.PicPayChallenger.model.dto.UserRegisterResponseDto;
import io.github.im2back.PicPayChallenger.model.entities.user.User;
import io.github.im2back.PicPayChallenger.repositories.UserRepository;
import io.github.im2back.PicPayChallenger.service.util.NotificationRequestDto;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private NotificationService notificationService;

	@Transactional(readOnly = true)
	public User findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	@Transactional
	public UserRegisterResponseDto saveUser(UserRegisterRequestDto dto) {
		// Validações
		User savedUser = repository
				.save(new User(dto.fullName(), dto.identificationDocument(), dto.email(), dto.password(), dto.type()));

		UserRegisterResponseDto responseDto = new UserRegisterResponseDto(savedUser.getId(), savedUser.getFullName(),
				savedUser.getIdentificationDocument(), savedUser.getEmail(), savedUser.getPassword(),
				savedUser.getType(), savedUser.getWallet().getId());

		return responseDto;
	}

	@Transactional
	public void transfer(Long idPayer, Long idPayee, BigDecimal value) {
		// Validações
		finalizeTransfer(); // autorizador externo

		User userPayer = findById(idPayee);
		User userPayee = findById(idPayee);
		receivePayment(userPayer, userPayee, value); //transferencia

		repository.saveAll(Arrays.asList(userPayee, userPayer)); //persistindo a transferencia
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
		if (!authorizationService.authorizeTransfer()) {
			throw new RuntimeException("Transfer not authorized");
		}
	}

}
