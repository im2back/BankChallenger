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
class BalanceCheckValidationTest {

	@InjectMocks
	private BalanceCheckValidation balanceCheckValidation;

	@Mock
	private ClientResourceClient clientResourceClient;

	@Test
	@DisplayName("Não deveria lançar a exceção para saldo maior ou igual ao valor da transferencia")
	void valid() {
		// ARRANGE
		Long idUserPayer = 1L;
		Long idUserPayee = 2L;
		BigDecimal value = new BigDecimal(100);

		UserDto userDto = new UserDto(1l, "name", "123456", "jeff@gmail.com", "123456", "COMUM", new BigDecimal(100));
		ResponseEntity<UserDto> body = ResponseEntity.ok(userDto);
		BDDMockito.when(clientResourceClient.findUser(idUserPayer)).thenReturn(body);

		// ACT
		balanceCheckValidation.valid(idUserPayer, idUserPayee, value);

		// ASSERT
	}

	@Test
	@DisplayName("Deveria lançar a exceção TransferValidationException para saldo menor que a transferencia")
	void validCenario02() {
		// ARRANGE
		Long idUserPayer = 1L;
		Long idUserPayee = 2L;
		BigDecimal value = new BigDecimal(100);

		UserDto userDto = new UserDto(1l, "name", "123456", "jeff@gmail.com", "123456", "COMUM", new BigDecimal(50));
		ResponseEntity<UserDto> body = ResponseEntity.ok(userDto);
		BDDMockito.when(clientResourceClient.findUser(idUserPayer)).thenReturn(body);

		// ACT + ASSERT
		assertThrows(TransferValidationException.class, () -> {
			balanceCheckValidation.valid(idUserPayer, idUserPayee, value);
		});
		

	}

}
