package io.github.im2back.PicPayChallenger.validation.transfer;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import io.github.im2back.PicPayChallenger.exceptions.TransferValidationException;
import io.github.im2back.PicPayChallenger.model.entities.user.User;

@Component
public class BalanceCheckValidation implements TransferValidations {

	@Override
	public void valid(User userPayer, User userPayee, BigDecimal value) {
		BigDecimal userPayerbalance = userPayer.getWallet().getBalance();

		if (userPayerbalance.compareTo(value) < 0) {
			throw new TransferValidationException(
					"Insufficient balance. Current balance: " + userPayerbalance + ", Transaction amount: " + value);
		}
	}

}
