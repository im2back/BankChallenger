package io.github.im2back.usermicroservice.model.dto;

import io.github.im2back.usermicroservice.model.entities.user.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterRequestDto(
		
		@NotBlank
		String fullName,
		
		@NotBlank
		String identificationDocument,
		
		@NotBlank
		String email,
		
		@NotBlank
		String password,
		
		@NotNull
		UserType type

) {

}
