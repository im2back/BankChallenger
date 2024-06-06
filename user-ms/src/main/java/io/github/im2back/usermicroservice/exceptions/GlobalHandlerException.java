package io.github.im2back.usermicroservice.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.im2back.usermicroservice.service.exceptions.CannotBeDuplicatedException;
import io.github.im2back.usermicroservice.service.exceptions.InvalidFormatException;
import io.github.im2back.usermicroservice.service.exceptions.NotificationException;
import io.github.im2back.usermicroservice.service.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalHandlerException {

	@ExceptionHandler(CannotBeDuplicatedException.class)
	ResponseEntity<StandardError> cannotBeDuplicatedException(CannotBeDuplicatedException ex,
			HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("Conflict");
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.CONFLICT.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

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

	@ExceptionHandler(UserNotFoundException.class)
	ResponseEntity<StandardError> userNotFoundException(UserNotFoundException ex, HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("Not Found");
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(NotificationException.class)
	ResponseEntity<StandardError> notificationException(NotificationException ex, HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("SERVICE UNAVAILABLE");
		response.setMessage("O serviço de menssagem está temporariamente indisponível.");
		response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
	}

	
	@ExceptionHandler(InvalidFormatException.class)
	ResponseEntity<StandardError> invalidFormatException(InvalidFormatException ex, HttpServletRequest request) {

		StandardError response = new StandardError();
		response.setError("BAD REQUEST");
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
