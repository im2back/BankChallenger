package io.github.im2back.usermicroservice.validation.transfer;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.TransferValidationException;
import io.github.im2back.usermicroservice.service.exceptions.UserNotFoundException;

@Component
public class BalanceCheckValidation implements TransferValidations {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void valid(Long idUserPayer, Long idUserPayee, BigDecimal value) {

		var userPayer = userRepository.findById(idUserPayer).orElseThrow(() -> new UserNotFoundException(idUserPayer));

		BigDecimal userPayerbalance = userPayer.getWallet().getBalance();

		if (value.compareTo(new BigDecimal(0)) == 0) {
			throw new TransferValidationException("invalid transfer amount, " + "Transaction amount: " + value);
		}
		if (userPayerbalance.compareTo(value) < 0) {
			throw new TransferValidationException(
					"Insufficient balance. Current balance: " + userPayerbalance + ", Transaction amount: " + value);
		}

	}
}