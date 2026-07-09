package com.financialos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalName;

    private String goalType; // e.g. Farm, House, Marriage, Emergency Fund, Retirement

    private Double targetAmount;

    private Double currentAmount;

    private LocalDate targetDate;

    private String status; // e.g. In Progress, Completed, On Hold

    private String notes;

    public Goal() {
    }

    public Goal(String goalName, String goalType, Double targetAmount, LocalDate targetDate) {
        this.goalName = goalName;
        this.goalType = goalType;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
        this.targetDate = targetDate;
        this.status = "In Progress";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalType() {
        return goalType;
    }

    public void setGoalType(String goalType) {
        this.goalType = goalType;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Double getRemainingAmount() {
        return (targetAmount != null && currentAmount != null) ? targetAmount - currentAmount : 0;
    }

    public Double getProgressPercentage() {
        if (targetAmount == null || targetAmount == 0) return 0.0;
        return (currentAmount / targetAmount) * 100;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
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

