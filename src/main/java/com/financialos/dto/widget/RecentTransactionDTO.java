package com.financialos.dto.widget;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecentTransactionDTO {
    private Long id;
    private String transactionReference;
    private String category;
    private BigDecimal amount;
    private LocalDateTime occurredAt;

    public RecentTransactionDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}

