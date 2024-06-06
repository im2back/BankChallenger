package io.github.im2back.transfermicroservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.im2back.transfermicroservice.clienthttp.ClientResourceClient;
import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.validation.transfer.TransferValidations;

@Service
public class TransferService {

	@Autowired
	private List<TransferValidations> transferValidations;

	@Autowired
	private AuthorizationService authorizationService;


	@Autowired
	ClientResourceClient clientResourceClient;

	@Transactional
	public void transfer(Long idPayer, Long idPayee, BigDecimal value) {

		// validacoes de usuario
		transferValidations.forEach(valid -> valid.valid(idPayer, idPayee, value));

		// autorizador externo
		authorizationService.finalizeTransfer();

		// transferencia
		clientResourceClient.transfer(new TransferRequestDto(idPayer, idPayee, value));

	}

}
