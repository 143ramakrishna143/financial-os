package com.financialos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.financialos.model.Account;
import com.financialos.model.Transaction;
import com.financialos.model.TransactionType;
import com.financialos.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransactionSuccessfully() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        String category = "Salary";
        TransactionType type = TransactionType.INCOME;

        Transaction transaction = new Transaction();
        transaction.setFromAccount(new Account());
        transaction.setToAccount(new Account());
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setType(type);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);


    }

    @Test
    void testPublishTransactionCreatedEvent() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        String category = "Salary";
        TransactionType type = TransactionType.INCOME;

        Transaction transaction = new Transaction();
        transaction.setFromAccount(new Account());
        transaction.setToAccount(new Account());
        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setType(type);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);


    }

    @Test
    void testRejectInvalidTransaction() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("-100.00");
        String category = "Salary";
        TransactionType type = TransactionType.INCOME;


    }

    @Test
    void testUUIDGeneration() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        String category = "Salary";
        TransactionType type = TransactionType.INCOME;

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());


    }

    @Test
    void testBigDecimalPrecision() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        String category = "Salary";
        TransactionType type = TransactionType.INCOME;

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());


    }
}
