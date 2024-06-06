package io.github.im2back.usermicroservice.validation.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.im2back.usermicroservice.model.entities.user.UserGeneric;
import io.github.im2back.usermicroservice.repositories.UserRepository;
import io.github.im2back.usermicroservice.service.exceptions.CannotBeDuplicatedException;
import io.github.im2back.usermicroservice.util.UtilsTest;


@ExtendWith(MockitoExtension.class)
class EmailCannotBeDuplicatedTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private EmailCannotBeDuplicated emailCannotBeDuplicated;
	
	@Test
	@DisplayName("Deveria lançar uma exceção do tipo CannotBeDuplicatedException para um email existente")
	void valid() {
		
		// ARRANGE
		Optional<UserGeneric> user = Optional.ofNullable(UtilsTest.userComum);
		BDDMockito.when(userRepository.findByEmail(UtilsTest.userRegisterRequest.email())).thenReturn(user);

		// ACT + ASSERT
		assertThrows(CannotBeDuplicatedException.class,
				() -> emailCannotBeDuplicated.valid(UtilsTest.userRegisterRequest));
		
	}
	
	@Test
	@DisplayName("Não deveria lançar uma exceção")
	void shouldNotThrowExceptionWhenDocumentIsNotUsed() {
		// ARRANGE
		Optional<UserGeneric> user = Optional.empty();
		BDDMockito.when(userRepository.findByEmail(UtilsTest.userRegisterRequest.email())).thenReturn(user);

		// ACT + ASSERT
		assertDoesNotThrow(() -> emailCannotBeDuplicated.valid(UtilsTest.userRegisterRequest));
	}


}
