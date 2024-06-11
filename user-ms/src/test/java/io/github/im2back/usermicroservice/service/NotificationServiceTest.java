package io.github.im2back.usermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import io.github.im2back.usermicroservice.service.exceptions.NotificationException;
import io.github.im2back.usermicroservice.service.util.NotificationRequestDto;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

	@InjectMocks
	private NotificationService notificationService;

	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(notificationService, "notificationUrl", "https://util.devi.tools/api/v1/notify");
	}

	@Test
	@DisplayName("Deveria enviar com sucesso")
	void sendNotification() {
		// arrange

		String message = "Notification message";

		NotificationRequestDto request = new NotificationRequestDto("jeff@gmail.com", message);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// act +assert
		assertDoesNotThrow(() -> notificationService.sendNotification(request));

	}
	
	  @Test
	    @DisplayName("Deveria lançar uma NotificationException ao falhar no envio")
	    void sendNotificationCenario02() {
	        // arrange
	        NotificationService notificationService = BDDMockito.mock(NotificationService.class);
	        String message = "Notification message";
	        NotificationRequestDto request = new NotificationRequestDto("jeff@gmail.com", message);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // Simula que o método sendNotification lança uma NotificationException
	        BDDMockito.willThrow(new NotificationException("Failed to send notification"))
	                  .given(notificationService).sendNotification(any(NotificationRequestDto.class));

	        // act + assert
	        assertThrows(NotificationException.class, () -> notificationService.sendNotification(request));
	    }
}
