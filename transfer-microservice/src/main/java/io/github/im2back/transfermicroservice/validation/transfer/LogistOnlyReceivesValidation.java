package io.github.im2back.transfermicroservice.validation.transfer;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.transfermicroservice.clienthttp.ClientResourceClient;
import io.github.im2back.transfermicroservice.dto.UserDto;
import io.github.im2back.transfermicroservice.service.exceptions.TransferValidationException;

@Component
public class LogistOnlyReceivesValidation implements TransferValidations {

	@Autowired
	private ClientResourceClient clientResourceClient;

	@Override
	public void valid(Long idUserPayer, Long idUserPayee, BigDecimal value) {

		var response = clientResourceClient.findUser(idUserPayer);

		UserDto userPayer = response.getBody();

		String type = userPayer.type();

		if (type.equals("LOGISTA")) {
			throw new TransferValidationException("Logist user cannot make payments");
		}
	}

}
