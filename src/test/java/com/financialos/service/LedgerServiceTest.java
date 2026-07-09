package com.financialos.service;

import com.financialos.model.Account;
import com.financialos.model.Transaction;
import com.financialos.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LedgerServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private LedgerService ledgerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBalanceForAccount_NoTransactions() {
        Long accountId = 1L;
        when(transactionRepository.findByToAccount_Id(accountId)).thenReturn(List.of());
        when(transactionRepository.findByFromAccount_Id(accountId)).thenReturn(List.of());

        BigDecimal balance = ledgerService.getBalanceForAccount(accountId);

        assertEquals(BigDecimal.ZERO, balance);
    }

    @Test
    public void testGetBalanceForAccount_CreditsOnly() {
        Long accountId = 1L;
        Transaction creditTransaction = new Transaction();
        creditTransaction.setAmount(new BigDecimal("100.00"));
        when(transactionRepository.findByToAccount_Id(accountId)).thenReturn(List.of(creditTransaction));
        when(transactionRepository.findByFromAccount_Id(accountId)).thenReturn(List.of());

        BigDecimal balance = ledgerService.getBalanceForAccount(accountId);

        assertEquals(new BigDecimal("100.00"), balance);
    }

    @Test
    public void testGetBalanceForAccount_DebitsOnly() {
        Long accountId = 1L;
        Transaction debitTransaction = new Transaction();
        debitTransaction.setAmount(new BigDecimal("-50.00"));
        when(transactionRepository.findByToAccount_Id(accountId)).thenReturn(List.of());
        when(transactionRepository.findByFromAccount_Id(accountId)).thenReturn(List.of(debitTransaction));

        BigDecimal balance = ledgerService.getBalanceForAccount(accountId);

        assertEquals(new BigDecimal("-50.00"), balance);
    }

    @Test
    public void testGetBalanceForAccount_CreditsAndDebits() {
        Long accountId = 1L;
        Transaction creditTransaction = new Transaction();
        creditTransaction.setAmount(new BigDecimal("200.00"));
        Transaction debitTransaction = new Transaction();
        debitTransaction.setAmount(new BigDecimal("-150.00"));
        when(transactionRepository.findByToAccount_Id(accountId)).thenReturn(List.of(creditTransaction));
        when(transactionRepository.findByFromAccount_Id(accountId)).thenReturn(List.of(debitTransaction));

        BigDecimal balance = ledgerService.getBalanceForAccount(accountId);

        assertEquals(new BigDecimal("50.00"), balance);
    }

    @Test
    public void testGetTotalBalanceAllAccounts_NoTransactions() {
        when(transactionRepository.findAll()).thenReturn(List.of());

        BigDecimal totalBalance = ledgerService.getTotalBalanceAllAccounts();

        assertEquals(BigDecimal.ZERO, totalBalance);
    }

    @Test
    public void testGetTotalBalanceAllAccounts_SingleAccount() {
        Long accountId = 1L;
        Transaction creditTransaction = new Transaction();
        creditTransaction.setAmount(new BigDecimal("100.00"));
        when(transactionRepository.findAll()).thenReturn(List.of(creditTransaction));

        BigDecimal totalBalance = ledgerService.getTotalBalanceAllAccounts();

        assertEquals(new BigDecimal("100.00"), totalBalance);
    }

    @Test
    public void testGetTotalBalanceAllAccounts_MultipleAccounts() {
        Long accountId1 = 1L;
        Long accountId2 = 2L;
        Transaction creditTransaction1 = new Transaction();
        creditTransaction1.setAmount(new BigDecimal("100.00"));
        Transaction debitTransaction1 = new Transaction();
        debitTransaction1.setAmount(new BigDecimal("-50.00"));
        Transaction creditTransaction2 = new Transaction();
        creditTransaction2.setAmount(new BigDecimal("200.00"));
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(creditTransaction1, debitTransaction1, creditTransaction2));

        BigDecimal totalBalance = ledgerService.getTotalBalanceAllAccounts();

        assertEquals(new BigDecimal("150.00"), totalBalance);
    }

    @Test
    public void testGetTotalBalanceAllAccounts_ExceptionHandling() {
        when(transactionRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> ledgerService.getTotalBalanceAllAccounts());
    }
}
