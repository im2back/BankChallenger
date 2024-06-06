package io.github.im2back.usermicroservice.validation.user;

import org.springframework.stereotype.Component;

import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;
import io.github.im2back.usermicroservice.service.exceptions.InvalidFormatException;

@Component
public class InvalidDocumentFormat implements UserRegistrationValidation {

	@Override
	public void valid(UserRegisterRequestDto dto) {

		if (!isCpf(dto.identificationDocument()) && !isCnpj(dto.identificationDocument())) {
			throw new InvalidFormatException("Invalid format for document");
		} 
	}

	private static boolean isCpf(String document) {
		return document.matches("\\d{11}");
	}

	private static boolean isCnpj(String document) {
		return document.matches("\\d{14}");
	}
}
