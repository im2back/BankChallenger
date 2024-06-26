package io.github.im2back.usermicroservice.model.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record TransferRequestDto(
		@NotNull 
		Long idPayer,

		@NotNull 
		Long idPayee,

		@NotNull 
		BigDecimal value, 
		
		@NotNull 
		Long idTransaction
		) {

}
