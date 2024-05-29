package io.github.im2back.transfermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/transfer")
public class TransferController {
	
	@Autowired
	private TransferService service;
	
	@Operation(summary = "Inicia uma transferencia e não retorna body em caso de sucesso")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Retorna uma status 200ok em caso de sucesso"),
		@ApiResponse(responseCode = "400", description = "Retorna uma exceção TransferValidationException caso o saldo seja insuficiente"),
		@ApiResponse(responseCode = "400", description = "Retorna uma exceção TransferValidationException caso o payer n'ao possua autoridade"),
		@ApiResponse(responseCode = "401", description = "Retorna uma exceção AuthorizationException caso o autorizador externo retorne resposta negativa"),
		@ApiResponse(responseCode = "503 ", description = "Retorna uma exceção NotificationException caso o serviço de notificação esteje indisponivel"),
	})
	@PostMapping
	public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequestDto dto) {
		service.transfer(dto.idPayer(), dto.idPayee(), dto.value());
		return ResponseEntity.ok().build();
	}

}
