package com.financialos.controller;

import com.financialos.dto.DashboardSummary;
import com.financialos.model.Expense;
import com.financialos.model.Income;
import com.financialos.repository.ExpenseRepository;
import com.financialos.repository.IncomeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public DashboardController(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/api/dashboard")
    public DashboardSummary getDashboard() {
        double totalIncome = incomeRepository.findAll().stream()
                .mapToDouble(Income::getAmount)
                .sum();

        double totalExpense = expenseRepository.findAll().stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double surplus = totalIncome - totalExpense;

        // Phase 1: net worth = cumulative surplus (no assets/MF/stocks yet - that's Phase 2)
        double netWorth = surplus;

        return new DashboardSummary(totalIncome, totalExpense, surplus, netWorth);
    }
}
