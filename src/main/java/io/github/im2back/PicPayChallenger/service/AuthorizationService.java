package io.github.im2back.PicPayChallenger.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.im2back.PicPayChallenger.service.util.AuthorizationResponseDto;

@Service
public class AuthorizationService {

	private final RestTemplate restTemplate;
	@Value("${url.transfer}") 
	private String authorizationUrl;

	public AuthorizationService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public boolean authorizeTransfer() {

		AuthorizationResponseDto response = restTemplate.getForObject(authorizationUrl, AuthorizationResponseDto.class);

		return response != null && response.data().authorization();
	}
}
