package com.financialos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardName;

    private String cardIssuer; // e.g. HDFC, ICICI, Axis, SBI

    private String last4Digits;

    private Double creditLimit;

    private Double currentBalance;

    private Double minDueAmount;

    private LocalDate dueDate;

    private Double interestRate;

    private String status; // e.g. Active, Inactive, Closed

    private String notes;

    public CreditCard() {
    }

    public CreditCard(String cardName, String cardIssuer, String last4Digits, 
                      Double creditLimit, LocalDate dueDate) {
        this.cardName = cardName;
        this.cardIssuer = cardIssuer;
        this.last4Digits = last4Digits;
        this.creditLimit = creditLimit;
        this.currentBalance = 0.0;
        this.dueDate = dueDate;
        this.status = "Active";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getLast4Digits() {
        return last4Digits;
    }

    public void setLast4Digits(String last4Digits) {
        this.last4Digits = last4Digits;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Double getAvailableCredit() {
        return (creditLimit != null && currentBalance != null) ? creditLimit - currentBalance : 0;
    }

    public Double getUtilizationPercentage() {
        if (creditLimit == null || creditLimit == 0) return 0.0;
        return (currentBalance / creditLimit) * 100;
    }

    public Double getMinDueAmount() {
        return minDueAmount;
    }

    public void setMinDueAmount(Double minDueAmount) {
        this.minDueAmount = minDueAmount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

