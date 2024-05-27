package io.github.im2back.PicPayChallenger.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.github.im2back.PicPayChallenger.service.exceptions.AuthorizationException;
import io.github.im2back.PicPayChallenger.service.util.AuthorizationResponseDto;

@Service
public class AuthorizationService {

	private final RestTemplate restTemplate;
	@Value("${url.transfer}")
	private String authorizationUrl;

	public AuthorizationService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<AuthorizationResponseDto> authorizeTransfer() {
		try {
			ResponseEntity<AuthorizationResponseDto> response = restTemplate.exchange(authorizationUrl, HttpMethod.GET,
					null, AuthorizationResponseDto.class);
			return response;

		} catch (HttpClientErrorException.Forbidden e) {
			throw new AuthorizationException("Transfer not authorized");
		}
	}

	public void finalizeTransfer() {
		ResponseEntity<AuthorizationResponseDto> response = authorizeTransfer();

		AuthorizationResponseDto body = response.getBody();
		Integer responseStatus = response.getStatusCode().value();

		String statusMessage = body.status();
		boolean isAuthorized = body.data().authorization();

		if (!isAuthorized || "fail".equals(statusMessage) || responseStatus != 200) {
			throw new AuthorizationException("Transfer not authorized");
		}
	}
}