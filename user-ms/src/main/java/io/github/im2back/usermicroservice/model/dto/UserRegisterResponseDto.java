package io.github.im2back.usermicroservice.model.dto;

import java.math.BigDecimal;

import io.github.im2back.usermicroservice.model.entities.user.User;
import io.github.im2back.usermicroservice.model.entities.user.UserType;

public record UserRegisterResponseDto(Long id,

		String fullName,

		String identificationDocument,

		String email,

		String password,

		UserType type,

		BigDecimal walletBalance) {

	public UserRegisterResponseDto(User user) {
		this(user.getId(), user.getFullName(), user.getIdentificationDocument(), user.getEmail(), user.getPassword(),
				user.getType(), user.getWallet().getBalance());
	}

}
