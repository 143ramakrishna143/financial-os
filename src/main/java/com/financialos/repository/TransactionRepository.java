package com.financialos.repository;

import com.financialos.model.Transaction;
import com.financialos.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByType(TransactionType type);
    List<Transaction> findByOccurredAtBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findByFromAccount_Id(Long fromAccountId);
    List<Transaction> findByToAccount_Id(Long toAccountId);
}

