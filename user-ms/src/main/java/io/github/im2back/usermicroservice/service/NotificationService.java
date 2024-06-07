package io.github.im2back.usermicroservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import io.github.im2back.usermicroservice.service.exceptions.NotificationException;
import io.github.im2back.usermicroservice.service.util.NotificationRequestDto;

@Service
public class NotificationService {

	private final RestTemplate restTemplate;

	@Value("${url.notify}")
	private String notificationUrl;

	public NotificationService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000), retryFor = {
			HttpServerErrorException.GatewayTimeout.class })
	public void sendNotification(NotificationRequestDto request) {

		// criação de cabeçalhos
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Criação da entidade que vai encapsular o cabeçalho e o corpo da requisição
		HttpEntity<NotificationRequestDto> entity = new HttpEntity<>(request, headers);

		// fazendo a requisição
		try {
			restTemplate.exchange(notificationUrl, HttpMethod.POST, entity, Void.class);
		} catch (HttpServerErrorException.GatewayTimeout ex) {
			throw new NotificationException(ex.getMessage());
		}

	}
}