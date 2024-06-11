package io.github.im2back.transfermicroservice.dto;

public record ResultTransferDto(
		Long idTransfer, String message, boolean isAuthorize) {

}
