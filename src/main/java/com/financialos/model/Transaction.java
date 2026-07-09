package com.financialos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private String category; // "Salary", "Food", "Stock Buy" etc.

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount; // source account (nullable for INCOME if from external)

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount; // destination account (nullable for EXPENSE if to external)

    @Column(name = "transaction_reference", unique = true)
    private String transactionReference;

    private BigDecimal amount;
    private LocalDateTime occurredAt;

    private String notes;

    public Transaction() {}

    public Transaction(TransactionType type, String category, Account fromAccount, Account toAccount, BigDecimal amount, LocalDateTime occurredAt, String notes) {
        this.type = type;
        this.category = category;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.occurredAt = occurredAt;
        this.notes = notes;
        this.transactionReference = UUID.randomUUID().toString();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Account getFromAccount() { return fromAccount; }
    public void setFromAccount(Account fromAccount) { this.fromAccount = fromAccount; }

    public Account getToAccount() { return toAccount; }
    public void setToAccount(Account toAccount) { this.toAccount = toAccount; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

