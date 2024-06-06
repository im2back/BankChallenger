package io.github.im2back.transfermicroservice.validation.transfer;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.im2back.transfermicroservice.clienthttp.ClientResourceClient;
import io.github.im2back.transfermicroservice.dto.UserDto;
import io.github.im2back.transfermicroservice.service.exceptions.TransferValidationException;

@Component
public class LogistOnlyReceivesValidation implements TransferValidations {

	@Autowired
	private ClientResourceClient clientResourceClient;

	private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");

	@Override
	public void valid(Long idUserPayer, Long idUserPayee, BigDecimal value) {

		var response = clientResourceClient.findUser(idUserPayer);

		UserDto userPayer = response.getBody();

		String document = userPayer.identificationDocument();

		if (isCnpj(document)) {
			throw new TransferValidationException("Logist user cannot make payments");
		}
	}

	private boolean isCnpj(String document) {
		return CNPJ_PATTERN.matcher(document).matches();
	}
}
