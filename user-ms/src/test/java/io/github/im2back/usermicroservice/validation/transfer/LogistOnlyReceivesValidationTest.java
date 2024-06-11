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
class LogistOnlyReceivesValidationTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private LogistOnlyReceivesValidation logistOnlyReceivesValidation;

	@Test
	@DisplayName("Não deveria lançar execeção para pagante do tipo comum")
	void valid() {
		// ARRAGE
		Long idPayer = 1l;
		Long idPayee = 2l;
		BigDecimal value = new BigDecimal(1000);

		Optional<User> userPayerOptional = Optional.ofNullable(UtilsTest.userComum);
		BDDMockito.when(userRepository.findById(idPayer)).thenReturn(userPayerOptional);

		// ACT +ASSERT

		assertDoesNotThrow(() -> logistOnlyReceivesValidation.valid(idPayer, idPayee, value));

	}

	@Test
	@DisplayName("deveria lançar execeção para pagante do tipo logista")
	void validCenario02() {
		// ARRAGE
		Long idPayer = 2l;
		Long idPayee = 1l;
		BigDecimal value = new BigDecimal(1);

		Optional<User> userPayerOptional = Optional.ofNullable(UtilsTest.userLogista);
		BDDMockito.when(userRepository.findById(idPayer)).thenReturn(userPayerOptional);

		// ACT +ASSERT
		assertThrows(TransferValidationException.class,
				() -> logistOnlyReceivesValidation.valid(idPayer, idPayee, value));

	}

}
