package io.github.im2back.usermicroservice.model.dto;

import java.math.BigDecimal;

import io.github.im2back.usermicroservice.model.entities.user.UserComum;
import io.github.im2back.usermicroservice.model.entities.user.UserLogista;

public record UserRegisterResponseDto(
		Long id,			
		String fullName,	
		String password,
		String identificationDocument,
		String email,	
		BigDecimal walletBalance) {

	public UserRegisterResponseDto(UserComum user) {
		this(	user.getId(),
				user.getFullName(),
				user.getPassword(),
				user.getCpf(),
				user.getEmail(),
				user.getWallet().getBalance());
	}

	public UserRegisterResponseDto(UserLogista user) {
		this(	user.getId(), 
				user.getFullName(),
				user.getPassword(),
				user.getCnpj(), 
				user.getEmail(),
				user.getWallet().getBalance());
	}

}
