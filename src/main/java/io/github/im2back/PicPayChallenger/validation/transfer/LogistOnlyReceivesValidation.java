package io.github.im2back.PicPayChallenger.validation.transfer;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import io.github.im2back.PicPayChallenger.model.entities.user.User;
import io.github.im2back.PicPayChallenger.model.entities.user.UserType;
import io.github.im2back.PicPayChallenger.service.exceptions.TransferValidationException;

@Component
public class LogistOnlyReceivesValidation implements TransferValidations {

	@Override
	public void valid(User userPayer, User userPayee, BigDecimal value) {
		UserType type = userPayer.getType();
		
		if(type.equals(UserType.LOGISTA)) {
			throw new TransferValidationException("Logist user cannot make payments");
		}
	}

}
