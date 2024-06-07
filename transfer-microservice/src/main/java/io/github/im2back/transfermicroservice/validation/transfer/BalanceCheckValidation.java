package io.github.im2back.transfermicroservice.validation.transfer;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.transfermicroservice.clienthttp.ClientResourceClient;
import io.github.im2back.transfermicroservice.dto.UserDto;
import io.github.im2back.transfermicroservice.service.exceptions.TransferValidationException;

@Component
public class BalanceCheckValidation implements TransferValidations {

	@Autowired
	private ClientResourceClient clientResourceClient;

	@Override
	public void valid(Long idUserPayer, Long idUserPayee, BigDecimal value) {

		var response = clientResourceClient.findUser(idUserPayer);

		UserDto userPayer = response.getBody();

		BigDecimal userPayerbalance = userPayer.walletBalance();

		if (value.compareTo(new BigDecimal(0)) == 0) {
			throw new TransferValidationException(
					"invalid transfer amount, "+"Transaction amount: " + value);
		}
		if (userPayerbalance.compareTo(value) < 0 ) {
			throw new TransferValidationException(
					"Insufficient balance. Current balance: " + userPayerbalance + ", Transaction amount: " + value);
		}


	}
}