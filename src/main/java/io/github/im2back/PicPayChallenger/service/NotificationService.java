package io.github.im2back.PicPayChallenger.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import io.github.im2back.PicPayChallenger.service.util.NotificationRequestDto;

@Service
public class NotificationService {

	private final RestTemplate restTemplate;
	
	@Value("${url.notify}") 
	private String notificationUrl;
	
	  public NotificationService(RestTemplate restTemplate ) {
	        this.restTemplate = restTemplate;
	    }
	  
	    @Retryable(value = { HttpServerErrorException.class }, maxAttempts = 3, backoff = @Backoff(delay = 2000))
	    public void sendNotification(NotificationRequestDto request) {
	        restTemplate.postForObject(notificationUrl, request, Void.class);
	    }
	}

