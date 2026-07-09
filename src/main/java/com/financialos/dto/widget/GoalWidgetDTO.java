package com.financialos.dto.widget;

import java.math.BigDecimal;

public class GoalWidgetDTO {
    private Long id;
    private String name;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private Double progressPercent; // 0-100
    private Integer monthsRemaining;

    public GoalWidgetDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }

    public BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }

    public Double getProgressPercent() { return progressPercent; }
    public void setProgressPercent(Double progressPercent) { this.progressPercent = progressPercent; }

    public Integer getMonthsRemaining() { return monthsRemaining; }
    public void setMonthsRemaining(Integer monthsRemaining) { this.monthsRemaining = monthsRemaining; }
}

