package com.financialos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "insurance")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyName;

    private String insuranceType; // e.g. Health, Life, Motor, Home, Travel

    private String policyNumber;

    private Double premiumAmount;

    private LocalDate premiumDueDate;

    private LocalDate policyStartDate;

    private LocalDate policyEndDate;

    private String provider;

    private String status; // e.g. Active, Inactive, Expired

    private String notes;

    public Insurance() {
    }

    public Insurance(String policyName, String insuranceType, String policyNumber, 
                     Double premiumAmount, LocalDate policyStartDate, LocalDate policyEndDate) {
        this.policyName = policyName;
        this.insuranceType = insuranceType;
        this.policyNumber = policyNumber;
        this.premiumAmount = premiumAmount;
        this.policyStartDate = policyStartDate;
        this.policyEndDate = policyEndDate;
        this.status = "Active";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public LocalDate getPremiumDueDate() {
        return premiumDueDate;
    }

    public void setPremiumDueDate(LocalDate premiumDueDate) {
        this.premiumDueDate = premiumDueDate;
    }

    public LocalDate getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(LocalDate policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public LocalDate getPolicyEndDate() {
        return policyEndDate;
    }

    public void setPolicyEndDate(LocalDate policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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

