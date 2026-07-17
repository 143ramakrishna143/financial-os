package com.financialos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

        Transaction result = transactionService.createTransaction(fromAccountId, toAccountId, amount, category, type);
        assertNotNull(result);
        assertEquals(UUID.randomUUID().toString(), result.getId());
        assertEquals(fromAccountId, result.getFromAccount().getId());
        assertEquals(toAccountId, result.getToAccount().getId());
        assertEquals(amount, result.getAmount());
        assertEquals(category, result.getCategory());
        assertEquals(type, result.getType());
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

        transactionService.createTransaction(fromAccountId, toAccountId, amount, category, type);
        verify(eventPublisher).publishEvent(any(TransactionCreatedEvent.class));
    }

    @Test
    void testRejectInvalidTransaction() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("-100.00");
        String category = "Salary";
        TransactionType type = TransactionType.INCOME;

        assertThrows(IllegalArgumentException.class, () -> 
            transactionService.createTransaction(fromAccountId, toAccountId, amount, category, type)
        );
    }

    @Test
    void testUUIDGeneration() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        String category = "Salary";
        TransactionType type = TransactionType.INCOME;

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction result = transactionService.createTransaction(fromAccountId, toAccountId, amount, category, type);
        assertNotNull(result.getId());
    }

    @Test
    void testBigDecimalPrecision() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        String category = "Salary";
        TransactionType type = TransactionType.INCOME;

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction result = transactionService.createTransaction(fromAccountId, toAccountId, amount, category, type);
        assertEquals(amount, result.getAmount());
    }
}
