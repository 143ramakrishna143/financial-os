package com.financialos.controller;

import com.financialos.dto.DashboardSummary;
import com.financialos.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final BudgetService budgetService;
    private final MutualFundService mutualFundService;
    private final StockService stockService;
    private final GoalService goalService;
    private final LoanService loanService;
    private final CreditCardService creditCardService;

    public DashboardController(BudgetService budgetService, 
                                MutualFundService mutualFundService,
                                StockService stockService,
                                GoalService goalService,
                                LoanService loanService,
                                CreditCardService creditCardService) {
        this.budgetService = budgetService;
        this.mutualFundService = mutualFundService;
        this.stockService = stockService;
        this.goalService = goalService;
        this.loanService = loanService;
        this.creditCardService = creditCardService;
    }

    @GetMapping
    public ResponseEntity<DashboardSummary> getDashboard() {
        double totalIncome = budgetService.getTotalIncome();
        double totalExpense = budgetService.getTotalExpense();
        double surplus = budgetService.getSurplus();
        
        // Phase 1: net worth = cumulative surplus + investments
        double mfValue = mutualFundService.getTotalMutualFundCurrentValue();
        double stockValue = stockService.getTotalStockCurrentValue();
        double liabilities = loanService.getTotalRemainingLoanAmount() + creditCardService.getTotalCreditCardBalance();
        
        double netWorth = surplus + mfValue + stockValue - liabilities;

        DashboardSummary summary = new DashboardSummary(totalIncome, totalExpense, surplus, netWorth);
        summary.setMutualFundValue(mfValue);
        summary.setStockValue(stockValue);
        summary.setLiabilities(liabilities);
        summary.setGoalsProgress(goalService.getTotalGoalCurrentAmount());
        summary.setGoalsTarget(goalService.getTotalGoalTargetAmount());
        
        return ResponseEntity.ok(summary);
    }
}
