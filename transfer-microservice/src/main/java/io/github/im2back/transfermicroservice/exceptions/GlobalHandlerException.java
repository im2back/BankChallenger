package io.github.im2back.transfermicroservice.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.RetryableException;
import io.github.im2back.transfermicroservice.service.exceptions.AuthorizationException;
import io.github.im2back.transfermicroservice.service.exceptions.NotificationException;
import io.github.im2back.transfermicroservice.service.exceptions.TransferValidationException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<StandardErrorBeanValidation> methodArgumentNotValidException(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		List<String> messages = new ArrayList<>();

		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			messages.add(fieldError.getField() + " : " + fieldError.getDefaultMessage());
		});

		StandardErrorBeanValidation response = new StandardErrorBeanValidation();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setError("Bad Request");
		response.setPath(request.getRequestURI());
		response.setMessage(messages);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(TransferValidationException.class)
	ResponseEntity<StandardError> transferValidationException(TransferValidationException ex,
			HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("Bad request");
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(AuthorizationException.class)
	ResponseEntity<StandardError> authorizationException(AuthorizationException ex, HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("UNAUTHORIZED");
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(NotificationException.class)
	ResponseEntity<StandardError> notificationException(NotificationException ex, HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("SERVICE UNAVAILABLE");
		response.setMessage("O serviço de notificação está temporariamente indisponível.");
		response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
	}
	
	@ExceptionHandler(RetryableException.class)
	public ResponseEntity<StandardError> handleRetryableException(RetryableException ex, HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("SERVICE UNAVAILABLE");
		response.setMessage("O serviço está temporariamente indisponível. Por favor, tente novamente mais tarde.");
		response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
	}
	
	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<StandardError> unsupportedOperationException(UnsupportedOperationException ex, HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("Unsupported Operation");
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.NOT_IMPLEMENTED.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
	}
}
