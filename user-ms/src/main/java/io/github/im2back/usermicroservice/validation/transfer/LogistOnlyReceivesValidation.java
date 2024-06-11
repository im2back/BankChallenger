package io.github.im2back.usermicroservice.validation.transfer;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.usermicroservice.model.entities.user.UserType;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.TransferValidationException;
import io.github.im2back.usermicroservice.service.exceptions.UserNotFoundException;

@Component
public class LogistOnlyReceivesValidation implements TransferValidations {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void valid(Long idUserPayer, Long idUserPayee, BigDecimal value) {

		var userPayer = userRepository.findById(idUserPayer).orElseThrow(() -> new UserNotFoundException(idUserPayer));

		var type = userPayer.getType();

		if (type.equals(UserType.LOGISTA)) {
			throw new TransferValidationException("Logist user cannot make payments");
		}
	}

}
