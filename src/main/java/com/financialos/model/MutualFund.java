package com.financialos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mutual_funds")
public class MutualFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fundName;

    private String fundCode;

    private Double amountInvested;

    private Double currentValue;

    private Double sipAmount;

    private LocalDate sipStartDate;

    private LocalDate investmentDate;

    private String notes;

    public MutualFund() {
    }

    public MutualFund(String fundName, String fundCode, Double amountInvested, 
                      Double currentValue, Double sipAmount, LocalDate sipStartDate) {
        this.fundName = fundName;
        this.fundCode = fundCode;
        this.amountInvested = amountInvested;
        this.currentValue = currentValue;
        this.sipAmount = sipAmount;
        this.sipStartDate = sipStartDate;
        this.investmentDate = LocalDate.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public Double getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(Double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getProfit() {
        return (currentValue != null && amountInvested != null) ? currentValue - amountInvested : 0;
    }

    public Double getSipAmount() {
        return sipAmount;
    }

    public void setSipAmount(Double sipAmount) {
        this.sipAmount = sipAmount;
    }

    public LocalDate getSipStartDate() {
        return sipStartDate;
    }

    public void setSipStartDate(LocalDate sipStartDate) {
        this.sipStartDate = sipStartDate;
    }

    public LocalDate getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(LocalDate investmentDate) {
        this.investmentDate = investmentDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

