package io.github.im2back.usermicroservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequestDto(
		
		@NotBlank
		String fullName,
		
		@NotBlank
		String identificationDocument,
		
		@NotBlank
		String email,
		
		@NotBlank
		String password

) {

}
