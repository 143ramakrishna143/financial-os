package com.financialos.dto;

import com.financialos.dto.widget.*;

import java.util.List;

public class DashboardOverviewDTO {
    private NetWorthWidgetDTO netWorth;
    private CashFlowWidgetDTO cashFlow;
    private InvestmentWidgetDTO investments;
    private List<GoalWidgetDTO> goals;
    private List<RecentTransactionDTO> recentTransactions;
    // upcoming bills omitted for now
    private FinancialHealthWidgetDTO health;

    public DashboardOverviewDTO() {}

    public NetWorthWidgetDTO getNetWorth() { return netWorth; }
    public void setNetWorth(NetWorthWidgetDTO netWorth) { this.netWorth = netWorth; }

    public CashFlowWidgetDTO getCashFlow() { return cashFlow; }
    public void setCashFlow(CashFlowWidgetDTO cashFlow) { this.cashFlow = cashFlow; }

    public InvestmentWidgetDTO getInvestments() { return investments; }
    public void setInvestments(InvestmentWidgetDTO investments) { this.investments = investments; }

    public List<GoalWidgetDTO> getGoals() { return goals; }
    public void setGoals(List<GoalWidgetDTO> goals) { this.goals = goals; }

    public List<RecentTransactionDTO> getRecentTransactions() { return recentTransactions; }
    public void setRecentTransactions(List<RecentTransactionDTO> recentTransactions) { this.recentTransactions = recentTransactions; }

    public FinancialHealthWidgetDTO getHealth() { return health; }
    public void setHealth(FinancialHealthWidgetDTO health) { this.health = health; }
}

