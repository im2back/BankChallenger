package io.github.im2back.PicPayChallenger.validation.user;

import io.github.im2back.PicPayChallenger.model.dto.UserRegisterRequestDto;

public interface UserRegistrationValidation {
	
	public void valid(UserRegisterRequestDto dto);
}
