package com.financialos.service;

import com.financialos.model.Account;
import com.financialos.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinanceFacade {

    private final FinanceEngineService financeEngineService;
    private final LedgerService ledgerService;
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final GoalService goalService;

    public FinanceFacade(FinanceEngineService financeEngineService,
                         LedgerService ledgerService,
                         TransactionService transactionService,
                         AccountService accountService,
                         GoalService goalService) {
        this.financeEngineService = financeEngineService;
        this.ledgerService = ledgerService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.goalService = goalService;
    }

    public Map<String, Object> getNetWorth() {
        return financeEngineService.calculateNetWorth();
    }

    public Map<String, Object> getCashFlow(LocalDateTime start, LocalDateTime end) {
        return financeEngineService.cashFlowForPeriod(start, end);
    }

    public List<Transaction> getRecentTransactions(int limit) {
        List<Transaction> all = transactionService.getAll();
        List<Transaction> sorted = all.stream()
                .sorted(Comparator.comparing(Transaction::getOccurredAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(limit)
                .collect(Collectors.toList());
        return sorted;
    }

    public List<Map<String, Object>> getAccountsSummary() {
        List<Account> accounts = accountService.getAllAccounts();
        List<Map<String, Object>> out = new ArrayList<>();
        for (Account a : accounts) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", a.getId());
            m.put("name", a.getName());
            m.put("type", a.getType());
            try {
                m.put("balance", ledgerService.getBalanceForAccount(a.getId()));
            } catch (Exception ex) {
                m.put("balance", null);
            }
            out.add(m);
        }
        return out;
    }

    public Map<String, Object> getDashboardOverview(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> out = new HashMap<>();
        out.put("netWorth", getNetWorth());
        out.put("cashFlow", getCashFlow(start, end));
        out.put("accounts", getAccountsSummary());
        out.put("recentTransactions", getRecentTransactions(10));
        out.put("goalsSummary", goalService.getAllGoals());
        return out;
    }
}

