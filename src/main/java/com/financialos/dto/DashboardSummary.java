package com.financialos.dto;

public class DashboardSummary {

    private double totalIncome;
    private double totalExpense;
    private double surplus;
    private double netWorth;
    private double mutualFundValue;
    private double stockValue;
    private double liabilities;
    private double goalsProgress;
    private double goalsTarget;

    public DashboardSummary(double totalIncome, double totalExpense, double surplus, double netWorth) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.surplus = surplus;
        this.netWorth = netWorth;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getSurplus() {
        return surplus;
    }

    public double getNetWorth() {
        return netWorth;
    }

    public double getMutualFundValue() {
        return mutualFundValue;
    }

    public void setMutualFundValue(double mutualFundValue) {
        this.mutualFundValue = mutualFundValue;
    }

    public double getStockValue() {
        return stockValue;
    }

    public void setStockValue(double stockValue) {
        this.stockValue = stockValue;
    }

    public double getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(double liabilities) {
        this.liabilities = liabilities;
    }

    public double getGoalsProgress() {
        return goalsProgress;
    }

    public void setGoalsProgress(double goalsProgress) {
        this.goalsProgress = goalsProgress;
    }

    public double getGoalsTarget() {
        return goalsTarget;
    }

    public void setGoalsTarget(double goalsTarget) {
        this.goalsTarget = goalsTarget;
    }
}
