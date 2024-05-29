package io.github.im2back.transfermicroservice.validation.transfer;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import io.github.im2back.transfermicroservice.clienthttp.ClientResourceClient;
import io.github.im2back.transfermicroservice.dto.UserDto;
import io.github.im2back.transfermicroservice.service.exceptions.TransferValidationException;

@ExtendWith(MockitoExtension.class)
class LogistOnlyReceivesValidationTest {

	@InjectMocks
	private LogistOnlyReceivesValidation logistOnlyReceivesValidation;

	@Mock
	private ClientResourceClient clientResourceClient;

	@Test
	@DisplayName("Não deveria lançar uma exceção para transferencia COMUM -> ANY")
	void valid() {
		// ARRANGE

		Long idUserPayer = 1L;
		Long idUserPayee = 2L;
		BigDecimal value = new BigDecimal(100);

		UserDto userDto = new UserDto(1l, "name", "123456", "jeff@gmail.com", "123456", "COMUM", new BigDecimal(100));
		ResponseEntity<UserDto> body = ResponseEntity.ok(userDto);
		BDDMockito.when(clientResourceClient.findUser(idUserPayer)).thenReturn(body);

		// ACT
		logistOnlyReceivesValidation.valid(idUserPayer, idUserPayee, value);

		// ASSERT
		BDDMockito.then(clientResourceClient).should().findUser(idUserPayer);

	}

	@Test
	@DisplayName("Deveria lançar uma exceção para transferencia LOGISTA -> ANY")
	void validCenario02() {
		// ARRANGE

		Long idUserPayer = 1L;
		Long idUserPayee = 2L;
		BigDecimal value = new BigDecimal(100);

		UserDto userDto = new UserDto(1l, "name", "123456", "jeff@gmail.com", "123456", "LOGISTA", new BigDecimal(100));
		ResponseEntity<UserDto> body = ResponseEntity.ok(userDto);
		BDDMockito.when(clientResourceClient.findUser(idUserPayer)).thenReturn(body);

		// ACT
		assertThrows(TransferValidationException.class, () -> {
			logistOnlyReceivesValidation.valid(idUserPayer, idUserPayee, value);
		});

		// ASSERT
		BDDMockito.then(clientResourceClient).should().findUser(idUserPayer);

	}

}
