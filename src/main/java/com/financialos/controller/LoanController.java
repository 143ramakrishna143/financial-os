package com.financialos.controller;

import com.financialos.model.Loan;
import com.financialos.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Optional<Loan> loan = loanService.getLoanById(id);
        return loan.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan created = loanService.saveLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        Optional<Loan> existing = loanService.getLoanById(id);
        if (existing.isPresent()) {
            loan.setId(id);
            Loan updated = loanService.saveLoan(loan);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Loan>> getLoansByType(@PathVariable String type) {
        return ResponseEntity.ok(loanService.getLoansByType(type));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @GetMapping("/summary")
    public ResponseEntity<LoanSummary> getLoanSummary() {
        LoanSummary summary = new LoanSummary(
                loanService.getTotalLoanPrincipal(),
                loanService.getTotalRemainingLoanAmount(),
                loanService.getTotalMonthlyEmi()
        );
        return ResponseEntity.ok(summary);
    }

    public static class LoanSummary {
        public Double totalPrincipal;
        public Double totalRemaining;
        public Double totalMonthlyEmi;

        public LoanSummary(Double totalPrincipal, Double totalRemaining, Double totalMonthlyEmi) {
            this.totalPrincipal = totalPrincipal;
            this.totalRemaining = totalRemaining;
            this.totalMonthlyEmi = totalMonthlyEmi;
        }
    }
}

