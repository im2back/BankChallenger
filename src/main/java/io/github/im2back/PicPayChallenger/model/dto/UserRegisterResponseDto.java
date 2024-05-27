package io.github.im2back.PicPayChallenger.model.dto;

import io.github.im2back.PicPayChallenger.model.entities.user.UserType;

public record UserRegisterResponseDto(
		Long id,

		String fullName,

		String identificationDocument,

		String email,

		String password,

		UserType type,
		
		Long walletId
		) {

}
