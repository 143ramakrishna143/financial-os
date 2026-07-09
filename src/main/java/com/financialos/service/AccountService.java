package com.financialos.service;

import com.financialos.model.Account;
import com.financialos.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final LedgerService ledgerService;

    public AccountService(AccountRepository accountRepository, LedgerService ledgerService) {
        this.accountRepository = accountRepository;
        this.ledgerService = ledgerService;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }

    public List<Account> getActiveAccounts() {
        return accountRepository.findByActive(true);
    }

    @Transactional
    public Account createAccount(Account account) {
        if (account.getCreatedAt() == null) account.setCreatedAt(java.time.LocalDateTime.now());
        account.setUpdatedAt(java.time.LocalDateTime.now());
        if (account.getBalance() == null) account.setBalance(0.0);
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateAccount(Long id, Account updated) {
        Account existing = getById(id);
        existing.setName(updated.getName());
        existing.setType(updated.getType());
        existing.setCurrency(updated.getCurrency());
        existing.setNotes(updated.getNotes());
        existing.setActive(updated.getActive());
        existing.setUpdatedAt(java.time.LocalDateTime.now());
        if (updated.getBalance() != null) existing.setBalance(updated.getBalance());
        return accountRepository.save(existing);
    }

    public BigDecimal getTotalBalance() {
        return ledgerService.getTotalBalanceAllAccounts();
    }

    public BigDecimal getBalanceByType(String type) {
        return accountRepository.findByType(type).stream()
                .map(a -> ledgerService.getBalanceForAccount(a.getId()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

