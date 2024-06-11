package io.github.im2back.transfermicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

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

import io.github.im2back.transfermicroservice.model.transaction.Transaction;
import io.github.im2back.transfermicroservice.model.transaction.TransactionStatus;
import io.github.im2back.transfermicroservice.repositories.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

	@InjectMocks
	private TransactionService transactionService;

	@Mock
	private TransactionRepository repository;

	@Captor
	private ArgumentCaptor<Transaction> captorTransaction;

	@Test
	@DisplayName("Deveria salvar uma transação e retornar o seu id")
	void saveTransaction() {
		// ARRAGE

		Transaction transaction = new Transaction(1l, 1l, 2l, Instant.now().atZone(ZoneId.of("America/Sao_Paulo")),
				TransactionStatus.PROCESSING, "IN ANALYZING", new BigDecimal(100));

		BDDMockito.when(repository.save(any(Transaction.class))).thenReturn(transaction);

		// ACT
		var response = transactionService
				.saveTransaction(new Transaction(null, 1l, 2l, Instant.now().atZone(ZoneId.of("America/Sao_Paulo")),
						TransactionStatus.PROCESSING, "IN ANALYZING", new BigDecimal(100)));

		// ASSERT
		BDDMockito.then(repository).should().save(captorTransaction.capture());
		assertEquals(transaction.getIdPayer(), captorTransaction.getValue().getIdPayer(),
				"Deveria salvar o objeto recebido como parametro");

		assertEquals(transaction.getIdPayee(), captorTransaction.getValue().getIdPayee(),
				"Deveria salvar o objeto recebido como parametro");

		assertEquals(1l, response, "O id retornado pelo método deveria ser igual ao id retornado pelo banco de dados");
	}

	@Test
	@DisplayName("Deveria instanciar uma Transaction")
	void instatiateTransaciton() {
		// ARRAGE
		Long idPayer = 1l;
		Long idPayee = 2l;
		String description = "IN ANALYZING";
		BigDecimal value = new BigDecimal(10);
		TransactionStatus transactionStatus = TransactionStatus.PROCESSING;

		Transaction transaction = new Transaction(null, 1l, 2l, Instant.now().atZone(ZoneId.of("America/Sao_Paulo")),
				TransactionStatus.PROCESSING, "IN ANALYZING", new BigDecimal(10));

		// ACT
		var response = transactionService.instatiateTransaciton(idPayer, idPayee, description, value,
				transactionStatus);

		// ASSERT
		assertEquals(transaction, response);

	}

}
