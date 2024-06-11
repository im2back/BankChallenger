package io.github.im2back.transfermicroservice.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import io.github.im2back.transfermicroservice.model.transaction.Transaction;

public record TransactionsHistory(Long IdPayer, Long IdPayee, BigDecimal value, ZonedDateTime date, String status

) {
	public TransactionsHistory (Transaction t){
		this(t.getIdPayer(),t.getIdPayee(),t.getValue_transaction(),t.getDate(),t.getStatus().toString());
	}
}
