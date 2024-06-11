package io.github.im2back.transfermicroservice.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.im2back.transfermicroservice.amqp.TransactionPublisher;
import io.github.im2back.transfermicroservice.dto.TransferPublishDto;
import io.github.im2back.transfermicroservice.dto.TransferRequestDto;
import io.github.im2back.transfermicroservice.model.transaction.TransactionStatus;
import io.github.im2back.transfermicroservice.service.exceptions.AuthorizationException;

@Service
public class TransferService {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	TransactionPublisher transactionPublisher;

	@Autowired
	private TransactionService transactionService;

	public void transfer(Long idPayer, Long idPayee, BigDecimal value) throws JsonProcessingException {

		try {
			authorizationService.authorizeTransfer();
			finalizeTransfer(idPayer, idPayee, value);

		} catch (AuthorizationException e) {
			transactionService.saveTransaction(transactionService.instatiateTransaciton(idPayer, idPayee,
					e.getMessage(), value, TransactionStatus.CANCELED));
			throw e;
		}

	}

	public void finalizeTransfer(Long idPayer, Long idPayee, BigDecimal value) throws JsonProcessingException {

		// salvando a transferência com status de PROCESSAMENTO e pegando seu id
		var idTransaction = transactionService.saveTransaction(
				transactionService.instatiateTransaciton(idPayer, idPayee, "IN ANALYZING", value, TransactionStatus.PROCESSING));

		// enviando a transação para a fila do rabbitmq
		transactionPublisher
				.issueTransfer(new TransferPublishDto(new TransferRequestDto(idPayer, idPayee, value), idTransaction));

	}

}
