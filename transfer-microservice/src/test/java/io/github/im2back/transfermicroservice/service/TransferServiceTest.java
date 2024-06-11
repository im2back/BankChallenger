package io.github.im2back.transfermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.im2back.transfermicroservice.amqp.TransactionPublisher;
import io.github.im2back.transfermicroservice.model.transaction.Transaction;
import io.github.im2back.transfermicroservice.model.transaction.TransactionStatus;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

	@InjectMocks
	private TransferService transferService;

	@Mock
	private AuthorizationService authorizationService;

	@Mock
	TransactionPublisher transactionPublisher;

	@Mock
	private TransactionService transactionService;

	@Captor
	private ArgumentCaptor<Transaction> captorTransaction;

	@Test
	@DisplayName("deveria iniciar a transferencia e fazer os chamados")
	void transfer() throws JsonProcessingException {
		// ARRANGE
		Long idPayer = 1l;
		Long idPayee = 2l;
		BigDecimal value = new BigDecimal(100);

		Transaction transaction = new Transaction(null, idPayer, idPayee,
				Instant.now().atZone(ZoneId.of("America/Sao_Paulo")), TransactionStatus.PROCESSING, "IN ANALYZING",
				value);

		BDDMockito.when(transactionService.instatiateTransaciton(idPayer, idPayee, "IN ANALYZING", value,
				TransactionStatus.PROCESSING)).thenReturn(transaction);
		// ACT

		transferService.transfer(idPayer, idPayee, value);

		// ASSERT
		BDDMockito.then(transactionService).should().saveTransaction(captorTransaction.capture());
		assertEquals(transaction.getIdPayer(), captorTransaction.getValue().getIdPayer());

		BDDMockito.verify(authorizationService, times(1)).authorizeTransfer();

	}

}
