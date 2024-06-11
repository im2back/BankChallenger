package io.github.im2back.transfermicroservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record TransferPublishDto(	
		@NotNull 
		Long idPayer,

		@NotNull 
		Long idPayee,

		@NotNull 
		BigDecimal value, 
		
		@NotNull 
		Long idTransaction
		
		) {
	
	public TransferPublishDto(TransferRequestDto dto,Long idTransaction){
		this(dto.idPayer(),dto.idPayee(),dto.value(),idTransaction);
	}

}
