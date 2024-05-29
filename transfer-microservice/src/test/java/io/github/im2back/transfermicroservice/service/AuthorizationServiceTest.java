package io.github.im2back.transfermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.github.im2back.transfermicroservice.service.exceptions.AuthorizationException;
import io.github.im2back.transfermicroservice.service.util.AuthorizationResponseDto;
import io.github.im2back.transfermicroservice.service.util.Data;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private AuthorizationService authorizationService;

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(authorizationService, "authorizationUrl", "http://mocked.url/transfer");
	}

	@Test
	@DisplayName("Deveria autorizar e retornar um objeto da autorização + status 200")
	void authorizeTransfer() {
		// ARRANGE
		var method = HttpMethod.GET;

		AuthorizationResponseDto responseDto = new AuthorizationResponseDto("sucess", new Data(true));
		ResponseEntity<AuthorizationResponseDto> responseMethod = ResponseEntity.ok(responseDto);

		BDDMockito
				.when(restTemplate.exchange("http://mocked.url/transfer", method, null, AuthorizationResponseDto.class))
				.thenReturn(responseMethod);

		// ACT
		var response = authorizationService.authorizeTransfer();

		// ASSERT
		BDDMockito.then(restTemplate).should().exchange("http://mocked.url/transfer", method, null,
				AuthorizationResponseDto.class);

		assertEquals(200, response.getStatusCode().value(), "Deveria retornar o status 200 ");
		assertEquals(response.getBody().status(), "sucess");
		assertEquals(response.getBody().data().authorization(), true);

	}

	@Test
	@DisplayName("Deveria lançar uma exceção do tipo AuthorizationException ao receber uma  HttpClientErrorException")
	void authorizeTransferCenario02() {
		// ARRANGE
		var method = HttpMethod.GET;

		BDDMockito
				.when(restTemplate.exchange("http://mocked.url/transfer", method, null, AuthorizationResponseDto.class))
				.thenThrow(HttpClientErrorException.Forbidden.class);

		// ACT+ ASSERT

		assertThrows(AuthorizationException.class, () -> {
			authorizationService.authorizeTransfer();
		}, "Deveria lançar a exceção AuthorizationException ");
	}

	@Test
	@DisplayName("Não deveria lançar uma exceção")
	void finalizeTransfer() {
		// ARRANGE
		var method = HttpMethod.GET;

		AuthorizationResponseDto responseDto = new AuthorizationResponseDto("sucess", new Data(true));
		ResponseEntity<AuthorizationResponseDto> responseMethod = ResponseEntity.ok(responseDto);

		BDDMockito
				.when(restTemplate.exchange("http://mocked.url/transfer", method, null, AuthorizationResponseDto.class))
				.thenReturn(responseMethod);

		// ACT
		authorizationService.finalizeTransfer();

		// ASSERT
	}

	@Test
	@DisplayName("deveria lançar uma exceção ao obter um objeto inválido")
	void finalizeTransferCenario02() {
		// ARRANGE
		var method = HttpMethod.GET;

		BDDMockito
				.when(restTemplate.exchange("http://mocked.url/transfer", method, null, AuthorizationResponseDto.class))
				.thenThrow(HttpClientErrorException.Forbidden.class);

		// ACT + ASSERT
		assertThrows(AuthorizationException.class, () -> {
			authorizationService.finalizeTransfer();
		}, "Deveria lançar uma exceção do tipo AuthorizationException");

	}

}
