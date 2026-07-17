package com.financialos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FinanceFacadeTest {

    @Mock
    private FinanceEngineService financeEngineService;

    @InjectMocks
    private FinanceFacade financeFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testNetWorthCalculation() {
        Map<String, Object> netWorth = new HashMap<>();
        netWorth.put("totalBalance", new BigDecimal("1000.00"));
        netWorth.put("mutualFundValue", new BigDecimal("500.00"));
        netWorth.put("stockValue", new BigDecimal("300.00"));

        when(financeEngineService.calculateNetWorth()).thenReturn(netWorth);

        Map<String, Object> result = financeFacade.getNetWorth();
        assertEquals(new BigDecimal("1800.00"), (BigDecimal) result.get("netWorth"));
    }

    @Test
    void testCashFlowCalculation() {
        LocalDateTime start = LocalDateTime.now().minusDays(30);
        LocalDateTime end = LocalDateTime.now();

        Map<String, Object> cashFlow = new HashMap<>();
        cashFlow.put("inflow", new BigDecimal("500.00"));
        cashFlow.put("outflow", new BigDecimal("200.00"));

        when(financeEngineService.cashFlowForPeriod(start, end)).thenReturn(cashFlow);

        Map<String, Object> result = financeFacade.getCashFlow(start, end);
        assertEquals(new BigDecimal("300.00"), (BigDecimal) result.get("cashFlow"));
    }

    @Test
    void testDashboardOverviewAggregation() {
        LocalDateTime start = LocalDateTime.now().minusDays(30);
        LocalDateTime end = LocalDateTime.now();

        Map<String, Object> netWorth = new HashMap<>();
        netWorth.put("totalBalance", new BigDecimal("1000.00"));
        netWorth.put("mutualFundValue", new BigDecimal("500.00"));
        netWorth.put("stockValue", new BigDecimal("300.00"));

        Map<String, Object> cashFlow = new HashMap<>();
        cashFlow.put("inflow", new BigDecimal("500.00"));
        cashFlow.put("outflow", new BigDecimal("200.00"));

        List<Map<String, Object>> accountsSummary = Arrays.asList(
                new HashMap<String, Object>() {{
                    put("id", 1L);
                    put("name", "Savings");
                    put("type", "SAVINGS");
                    put("balance", new BigDecimal("500.00"));
                }},
                new HashMap<String, Object>() {{
                    put("id", 2L);
                    put("name", "Checking");
                    put("type", "CHECKING");
                    put("balance", new BigDecimal("-100.00"));
                }}
        );

        List<Transaction> recentTransactions = Arrays.asList(
                new Transaction() {{
                    setTransactionReference("TXN-001");
                    setCategory("Salary");
                    setAmount(new BigDecimal("500.00"));
                    setOccurredAt(LocalDateTime.now().minusDays(1));
                }},
                new Transaction() {{
                    setTransactionReference("TXN-002");
                    setCategory("Groceries");
                    setAmount(new BigDecimal("-100.00"));
                    setOccurredAt(LocalDateTime.now());
                }}
        );

        when(financeEngineService.calculateNetWorth()).thenReturn(netWorth);
        when(financeEngineService.cashFlowForPeriod(start, end)).thenReturn(cashFlow);
        when(accountService.getAllAccountsSummary()).thenReturn(accountsSummary);
        when(transactionService.getRecentTransactions(10)).thenReturn(recentTransactions);

        Map<String, Object> result = financeFacade.getDashboardOverview(start, end);
        assertEquals(new BigDecimal("1800.00"), (BigDecimal) result.get("netWorth"));
        assertEquals(new BigDecimal("300.00"), (BigDecimal) result.get("cashFlow"));
        assertEquals(2, ((List<Map<String, Object>>) result.get("accounts")).size());
        assertEquals(2, ((List<Transaction>) result.get("recentTransactions")).size());
    }

    @Test
    void testRecentTransactionsRetrieval() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction() {{
                    setTransactionReference("TXN-001");
                    setCategory("Salary");
                    setAmount(new BigDecimal("500.00"));
                    setOccurredAt(LocalDateTime.now().minusDays(1));
                }},
                new Transaction() {{
                    setTransactionReference("TXN-002");
                    setCategory("Groceries");
                    setAmount(new BigDecimal("-100.00"));
                    setOccurredAt(LocalDateTime.now());
                }}
        );

        when(transactionService.getRecentTransactions(10)).thenReturn(transactions);

        List<Transaction> result = financeFacade.getRecentTransactions(10);
        assertEquals(2, result.size());
    }
}
