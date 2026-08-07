package com.financialos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.financialos.model.Account;
import com.financialos.model.Transaction;
import com.financialos.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LedgerServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private LedgerService ledgerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreditTransactionIncreasesBalance() {
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal("100.00");
        Transaction transaction = new Transaction();
        transaction.setToAccount(new Account());
        transaction.setAmount(amount);

        when(transactionRepository.findByToAccount_Id(accountId)).thenReturn(Arrays.asList(transaction));

        assertEquals(new BigDecimal("100.00"), ledgerService.getBalanceForAccount(accountId));
    }

    @Test
    void testDebitTransactionDecreasesBalance() {
        Long accountId = 1L;
        BigDecimal amount = new BigDecimal("100.00");
        Transaction transaction = new Transaction();
        transaction.setFromAccount(new Account());
        transaction.setAmount(amount);

        when(transactionRepository.findByFromAccount_Id(accountId)).thenReturn(Arrays.asList(transaction));

        assertEquals(new BigDecimal("-100.00"), ledgerService.getBalanceForAccount(accountId));
    }

    @Test
    void testTransferUpdatesBalancesCorrectly() {
        Long fromAccountId = 1L;
        Long toAccountId = 2L;
        BigDecimal amount = new BigDecimal("100.00");
        Transaction transaction = new Transaction();
        transaction.setFromAccount(new Account());
        transaction.setToAccount(new Account());
        transaction.setAmount(amount);

        when(transactionRepository.findByToAccount_Id(toAccountId)).thenReturn(Arrays.asList(transaction));
        when(transactionRepository.findByFromAccount_Id(fromAccountId)).thenReturn(Arrays.asList(transaction));

        assertEquals(new BigDecimal("-100.00"), ledgerService.getBalanceForAccount(fromAccountId));
        assertEquals(new BigDecimal("100.00"), ledgerService.getBalanceForAccount(toAccountId));
    }

    @Test
    void testEmptyLedgerReturnsZero() {
        Long accountId = 1L;

        when(transactionRepository.findByToAccount_Id(accountId)).thenReturn(Arrays.asList());
        when(transactionRepository.findByFromAccount_Id(accountId)).thenReturn(Arrays.asList());

        assertEquals(BigDecimal.ZERO, ledgerService.getBalanceForAccount(accountId));
    }

    @Test
    void testMultipleTransactionsCalculateCorrectly() {
        Long accountId = 1L;
        BigDecimal amount1 = new BigDecimal("100.00");
        BigDecimal amount2 = new BigDecimal("50.00");

        Transaction transaction1 = new Transaction();
        transaction1.setToAccount(new Account());
        transaction1.setAmount(amount1);

        Transaction transaction2 = new Transaction();
        transaction2.setFromAccount(new Account());
        transaction2.setAmount(amount2);

        when(transactionRepository.findByToAccount_Id(accountId)).thenReturn(Arrays.asList(transaction1));
        when(transactionRepository.findByFromAccount_Id(accountId)).thenReturn(Arrays.asList(transaction2));

        assertEquals(new BigDecimal("50.00"), ledgerService.getBalanceForAccount(accountId));
    }
}
