package io.github.im2back.PicPayChallenger.model.dto;

import io.github.im2back.PicPayChallenger.model.entities.user.UserType;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequestDto(
		
		@NotBlank
		String fullName,
		
		@NotBlank
		String identificationDocument,
		
		@NotBlank
		String email,
		
		@NotBlank
		String password,
		
		@NotBlank
		UserType type

) {

}
