package com.financialos.service;

import com.financialos.model.Loan;
import com.financialos.repository.LoanRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public List<Loan> getLoansByType(String loanType) {
        return loanRepository.findByLoanType(loanType);
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findByStatus("Active");
    }

    public Double getTotalLoanPrincipal() {
        return loanRepository.findAll().stream()
                .mapToDouble(Loan::getPrincipalAmount)
                .sum();
    }

    public Double getTotalRemainingLoanAmount() {
        return loanRepository.findAll().stream()
                .mapToDouble(Loan::getRemainingAmount)
                .sum();
    }

    public Double getTotalMonthlyEmi() {
        return loanRepository.findAll().stream()
                .mapToDouble(Loan::getMonthlyEmi)
                .sum();
    }
}

