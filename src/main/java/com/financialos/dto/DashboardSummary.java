package com.financialos.dto;

public class DashboardSummary {

    private double totalIncome;
    private double totalExpense;
    private double surplus;
    private double netWorth; // Phase 1: simplified as cumulative income - expense

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
}
