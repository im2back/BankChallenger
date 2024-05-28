package io.github.im2back.usermicroservice.validation.user;

import io.github.im2back.usermicroservice.model.dto.UserRegisterRequestDto;

public interface UserRegistrationValidation {
	
	public void valid(UserRegisterRequestDto dto);
}
