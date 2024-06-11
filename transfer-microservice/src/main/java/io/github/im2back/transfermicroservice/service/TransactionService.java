package io.github.im2back.transfermicroservice.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.im2back.transfermicroservice.dto.TransactionsHistory;
import io.github.im2back.transfermicroservice.model.transaction.Transaction;
import io.github.im2back.transfermicroservice.model.transaction.TransactionStatus;
import io.github.im2back.transfermicroservice.repositories.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repository;

	public List<TransactionsHistory> findByIdPayerOrPayee(Long id) {

		return repository.findByIdPayerOrPayee(id).stream().map(e -> new TransactionsHistory(e))
				.collect(Collectors.toList());

	}

	public Long saveTransaction(Transaction transaction) {

		var transactionSave = repository.save(transaction);
		return transactionSave.getId();
	}

	protected Transaction instatiateTransaciton(Long idPayer, Long idPayee, String description, BigDecimal value,
			TransactionStatus transactionStatus) {
		return new Transaction(null, idPayer, idPayee, Instant.now().atZone(ZoneId.of("America/Sao_Paulo")),
				transactionStatus, description, value);
	}

	public void setStatus(Long id, boolean isAuthorized, String msg) {
		var transaction = repository.findById(id).get();

		TransactionStatus status = isAuthorized ? TransactionStatus.FINISHED : TransactionStatus.CANCELED;
		transaction.setStatus(status);
		transaction.setDescription(msg);

		repository.save(transaction);
	}
}
