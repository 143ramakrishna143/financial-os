package com.financialos.service;

import com.financialos.model.Expense;
import com.financialos.model.Income;
import com.financialos.repository.ExpenseRepository;
import com.financialos.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BudgetService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public BudgetService(IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    public Double getTotalIncome() {
        return incomeRepository.findAll().stream()
                .mapToDouble(Income::getAmount)
                .sum();
    }

    public Double getTotalExpense() {
        return expenseRepository.findAll().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Double getSurplus() {
        return getTotalIncome() - getTotalExpense();
    }

    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }

    public List<Income> getIncomeBySource(String source) {
        return incomeRepository.findBySource(source);
    }

    public Double getExpenseByCategory(String category) {
        return expenseRepository.findByCategory(category).stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Double getIncomeBySourceAmount(String source) {
        return incomeRepository.findBySource(source).stream()
                .mapToDouble(Income::getAmount)
                .sum();
    }
}

