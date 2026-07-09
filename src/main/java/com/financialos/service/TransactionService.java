package com.financialos.service;

import com.financialos.model.Account;
import com.financialos.model.Transaction;
import com.financialos.model.TransactionType;
import com.financialos.repository.TransactionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final ApplicationEventPublisher eventPublisher;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, ApplicationEventPublisher eventPublisher) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.eventPublisher = eventPublisher;
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found: " + id));
    }

    @Transactional
    public Transaction create(Transaction t) {
        if (t.getOccurredAt() == null) t.setOccurredAt(LocalDateTime.now());
        // Persist transaction only; ledger will derive balances. Publish event for listeners.
        Transaction saved = transactionRepository.save(t);
        try {
            eventPublisher.publishEvent(new com.financialos.service.event.TransactionCreatedEvent(this, saved));
        } catch (Exception ignored) {}
        return saved;
    }

    public List<Transaction> findByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    public List<Transaction> findBetween(LocalDateTime start, LocalDateTime end) {
        return transactionRepository.findByOccurredAtBetween(start, end);
    }
}

