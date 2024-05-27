package io.github.im2back.PicPayChallenger.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.im2back.PicPayChallenger.service.exceptions.AuthorizationException;
import io.github.im2back.PicPayChallenger.service.exceptions.CannotBeDuplicatedException;
import io.github.im2back.PicPayChallenger.service.exceptions.TransferValidationException;
import io.github.im2back.PicPayChallenger.service.exceptions.UserNotFoundException;
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
	
	@ExceptionHandler(UserNotFoundException.class)
	ResponseEntity<StandardError> userNotFoundException(UserNotFoundException ex,
			HttpServletRequest request) {
		
		StandardError response = new StandardError();
		response.setError("Not Found");
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);		
	}
	
	@ExceptionHandler(AuthorizationException.class)
	ResponseEntity<StandardError> authorizationException(AuthorizationException ex,
			HttpServletRequest request) {
		
		StandardError response = new StandardError();
		response.setError("UNAUTHORIZED");
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setPath(request.getRequestURI());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);		
	}
}
