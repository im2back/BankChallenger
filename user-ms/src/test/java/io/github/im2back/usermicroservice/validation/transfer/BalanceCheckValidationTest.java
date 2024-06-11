package io.github.im2back.usermicroservice.validation.transfer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.TransferValidationException;
import io.github.im2back.usermicroservice.util.UtilsTest;

@ExtendWith(MockitoExtension.class)
class BalanceCheckValidationTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private BalanceCheckValidation balanceCheckValidation;

	@Test
	@DisplayName("Deveria retornar uma exceção em caso de saldo insuficiente")
	void valid() {

		// ARRANGE
		Long idPayer = 1l;
		Long idPayee = 2l;
		BigDecimal value = new BigDecimal(1000);

		Optional<User> userPayerOptional = Optional.ofNullable(UtilsTest.userComum);

		BDDMockito.when(userRepository.findById(idPayer)).thenReturn(userPayerOptional);

		// ACT + ASSERT

		assertThrows(TransferValidationException.class, () -> balanceCheckValidation.valid(idPayer, idPayee, value));

	}

	@Test
	@DisplayName("Não Deveria lançar exceção caso o saldo seje diferente de 0 ou suficiente")
	void validCenario02() {

		// ARRANGE
		Long idPayer = 1l;
		Long idPayee = 2l;
		BigDecimal value = new BigDecimal(1);

		Optional<User> userPayerOptional = Optional.ofNullable(UtilsTest.userComum);

		BDDMockito.when(userRepository.findById(idPayer)).thenReturn(userPayerOptional);

		// ACT + assert
		assertDoesNotThrow(() -> balanceCheckValidation.valid(idPayer, idPayee, value));

	}
	
	@Test
	@DisplayName("Deveria lançar exceção caso o valor da transferencia seje 0")
	void validCenario03() {

		// ARRANGE
		Long idPayer = 1l;
		Long idPayee = 2l;
		BigDecimal value = new BigDecimal(0);

		Optional<User> userPayerOptional = Optional.ofNullable(UtilsTest.userComum);

		BDDMockito.when(userRepository.findById(idPayer)).thenReturn(userPayerOptional);

		// ACT + assert
		assertThrows(TransferValidationException.class, () -> balanceCheckValidation.valid(idPayer, idPayee, value));

	}


}
