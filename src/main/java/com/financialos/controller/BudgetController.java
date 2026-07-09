package com.financialos.controller;

import com.financialos.model.Expense;
import com.financialos.model.Income;
import com.financialos.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/summary")
    public ResponseEntity<BudgetSummary> getBudgetSummary() {
        BudgetSummary summary = new BudgetSummary(
                budgetService.getTotalIncome(),
                budgetService.getTotalExpense(),
                budgetService.getSurplus()
        );
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/expenses/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(budgetService.getExpensesByCategory(category));
    }

    @GetMapping("/income/source/{source}")
    public ResponseEntity<List<Income>> getIncomeBySource(@PathVariable String source) {
        return ResponseEntity.ok(budgetService.getIncomeBySource(source));
    }

    @GetMapping("/expenses/category/{category}/total")
    public ResponseEntity<Double> getExpenseByCategoryTotal(@PathVariable String category) {
        return ResponseEntity.ok(budgetService.getExpenseByCategory(category));
    }

    @GetMapping("/income/source/{source}/total")
    public ResponseEntity<Double> getIncomeBySourceTotal(@PathVariable String source) {
        return ResponseEntity.ok(budgetService.getIncomeBySourceAmount(source));
    }

    public static class BudgetSummary {
        public Double totalIncome;
        public Double totalExpense;
        public Double surplus;

        public BudgetSummary(Double totalIncome, Double totalExpense, Double surplus) {
            this.totalIncome = totalIncome;
            this.totalExpense = totalExpense;
            this.surplus = surplus;
        }
    }
}

