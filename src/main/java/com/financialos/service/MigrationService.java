package com.financialos.service;

import com.financialos.model.Account;
import com.financialos.model.Transaction;
import com.financialos.model.TransactionType;
import com.financialos.repository.AccountRepository;
import com.financialos.repository.ExpenseRepository;
import com.financialos.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MigrationService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    public MigrationService(IncomeRepository incomeRepository,
                             ExpenseRepository expenseRepository,
                             AccountRepository accountRepository,
                             TransactionService transactionService) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public String migrateLegacyData() {
        // Ensure default account exists
        Account defaultAccount = accountRepository.findByName("Default Account").orElseGet(() -> {
            Account a = new Account("Default Account", "BANK", 0.0, "INR");
            return accountRepository.save(a);
        });

        int incomeCount = 0;
        int expenseCount = 0;

        // Migrate incomes -> transactions (type=INCOME)
        List<com.financialos.model.Income> incomes = incomeRepository.findAll();
        for (com.financialos.model.Income inc : incomes) {
            Transaction t = new Transaction();
            t.setType(TransactionType.INCOME);
            t.setCategory(inc.getSource());
            t.setFromAccount(null);
            t.setToAccount(defaultAccount);
            if (inc.getAmount() != null) t.setAmount(java.math.BigDecimal.valueOf(inc.getAmount()));
            if (inc.getDate() != null) {
                t.setOccurredAt(inc.getDate().atTime(LocalTime.MIDNIGHT));
            } else {
                t.setOccurredAt(LocalDateTime.now());
            }
            t.setNotes("Legacy Income ID: " + inc.getId());
            transactionService.create(t);
            incomeCount++;
        }

        // Migrate expenses -> transactions (type=EXPENSE)
        List<com.financialos.model.Expense> expenses = expenseRepository.findAll();
        for (com.financialos.model.Expense exp : expenses) {
            Transaction t = new Transaction();
            t.setType(TransactionType.EXPENSE);
            t.setCategory(exp.getCategory());
            t.setFromAccount(defaultAccount);
            t.setToAccount(null);
            if (exp.getAmount() != null) t.setAmount(java.math.BigDecimal.valueOf(exp.getAmount()));
            if (exp.getDate() != null) {
                t.setOccurredAt(exp.getDate().atTime(LocalTime.MIDNIGHT));
            } else {
                t.setOccurredAt(LocalDateTime.now());
            }
            t.setNotes("Legacy Expense ID: " + exp.getId());
            transactionService.create(t);
            expenseCount++;
        }

        return "Migrated incomes: " + incomeCount + ", expenses: " + expenseCount + ", to default account id: " + defaultAccount.getId();
    }
}

