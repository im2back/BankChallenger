package io.github.im2back.transfermicroservice.dto;

import java.math.BigDecimal;

public record UserDto(Long id,

		String fullName,

		String identificationDocument,

		String email,

		String password,

		String type,

		BigDecimal walletBalance) {

}