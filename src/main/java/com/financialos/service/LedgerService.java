package com.financialos.service;

import com.financialos.model.Transaction;
import com.financialos.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LedgerService {

    private final TransactionRepository transactionRepository;

    public LedgerService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getBalanceForAccount(Long accountId) {
        BigDecimal credit = BigDecimal.ZERO;
        BigDecimal debit = BigDecimal.ZERO;

        List<Transaction> credits = transactionRepository.findByToAccount_Id(accountId);
        for (Transaction t : credits) {
            if (t.getAmount() != null) credit = credit.add(t.getAmount());
        }

        List<Transaction> debits = transactionRepository.findByFromAccount_Id(accountId);
        for (Transaction t : debits) {
            if (t.getAmount() != null) debit = debit.add(t.getAmount());
        }

        return credit.subtract(debit);
    }

    public BigDecimal getTotalBalanceAllAccounts() {
        // Sum balance of all accounts by iterating over all transactions' account ids is expensive.
        // For simplicity, sum balances for accounts that appear in to/from lists.
        // In future, use AccountRepository to list active accounts and sum per-account balances.
        List<Transaction> all = transactionRepository.findAll();
        java.util.Set<Long> accountIds = new java.util.HashSet<>();
        for (Transaction t : all) {
            if (t.getFromAccount() != null) accountIds.add(t.getFromAccount().getId());
            if (t.getToAccount() != null) accountIds.add(t.getToAccount().getId());
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Long id : accountIds) {
            total = total.add(getBalanceForAccount(id));
        }
        return total;
    }
}

