package io.github.im2back.transfermicroservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public record TransferRequestDto(
		@NotNull Long idPayer,

		@NotNull Long idPayee,

		@NotNull BigDecimal value)

{

}
