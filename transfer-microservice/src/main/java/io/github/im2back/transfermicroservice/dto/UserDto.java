package io.github.im2back.transfermicroservice.dto;

import java.math.BigDecimal;

public record UserDto(
		Long id,
		String fullName,
		String password,
		String identificationDocument,
		String email,
		BigDecimal walletBalance
		) {

}
