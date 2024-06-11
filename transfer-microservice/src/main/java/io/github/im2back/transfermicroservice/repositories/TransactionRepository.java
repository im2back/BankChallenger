package io.github.im2back.transfermicroservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import feign.Param;
import io.github.im2back.transfermicroservice.model.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	  @Query("SELECT t FROM Transaction t WHERE t.idPayer = :id OR t.idPayee = :id")
	    List<Transaction> findByIdPayerOrPayee(@Param("id") Long id);

}
