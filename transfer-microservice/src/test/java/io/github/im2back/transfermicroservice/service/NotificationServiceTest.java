package io.github.im2back.transfermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import io.github.im2back.transfermicroservice.service.exceptions.NotificationException;
import io.github.im2back.transfermicroservice.service.util.NotificationRequestDto;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private NotificationService notificationService;

	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(notificationService, "notificationUrl", "http://mocked.url/transfer");
	}

	@Test
	@DisplayName("Deveria enviar uma notificação e não retornar nada")
	void sendNotification() {
		// ARRANGE
		NotificationRequestDto dto = new NotificationRequestDto("jeff@gmail.com", "Pagamento recebido");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<NotificationRequestDto> entity = new HttpEntity<>(dto, headers);

		// ACT
		notificationService.sendNotification(dto);

		// ASSERT
		BDDMockito.then(restTemplate).should().exchange("http://mocked.url/transfer", HttpMethod.POST, entity,
				Void.class);

	}

	@Test
	@DisplayName("Deveria lançar uma exceção do tipo NotificationException")
	void sendNotificationCenario02() {
		// ARRANGE
		NotificationRequestDto dto = new NotificationRequestDto("jeff@gmail.com", "Pagamento recebido");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<NotificationRequestDto> entity = new HttpEntity<>(dto, headers);
		
		BDDMockito.when(restTemplate.exchange("http://mocked.url/transfer", HttpMethod.POST, entity, Void.class)).
		thenThrow(HttpServerErrorException.GatewayTimeout.class);
		
		// ACT + ASSERT
		assertThrows(NotificationException.class, () -> {
			notificationService.sendNotification(dto);
		});
		
				
	}

}
