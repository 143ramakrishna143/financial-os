package com.financialos.service;

import com.financialos.model.Transaction;
import com.financialos.model.TransactionType;
import com.financialos.repository.ExpenseRepository;
import com.financialos.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

@Service
public class FinanceEngineService {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final MutualFundService mutualFundService;
    private final StockService stockService;

    public FinanceEngineService(AccountService accountService,
                                TransactionService transactionService,
                                IncomeRepository incomeRepository,
                                ExpenseRepository expenseRepository,
                                MutualFundService mutualFundService,
                                StockService stockService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.mutualFundService = mutualFundService;
        this.stockService = stockService;
    }

    /**
     * Calculate net worth = total assets (account balances + investments) - liabilities
     */
    public Map<String, Object> calculateNetWorth() {
        Map<String, Object> out = new HashMap<>();

        BigDecimal totalBalance = accountService.getTotalBalance();
        BigDecimal mfValue = BigDecimal.ZERO;
        BigDecimal stockValue = BigDecimal.ZERO;
        try {
            Double mf = mutualFundService.getTotalMutualFundCurrentValue();
            if (mf != null) mfValue = BigDecimal.valueOf(mf);
        } catch (Exception ignored) {}
        try {
            Double sv = stockService.getTotalStockCurrentValue();
            if (sv != null) stockValue = BigDecimal.valueOf(sv);
        } catch (Exception ignored) {}

        BigDecimal assets = (totalBalance != null ? totalBalance : BigDecimal.ZERO).add(mfValue).add(stockValue);
        // For now liabilities are not separated; future: subtract loans/credit cards
        out.put("totalBalance", totalBalance);
        out.put("mutualFundValue", mfValue);
        out.put("stockValue", stockValue);
        out.put("assets", assets);
        out.put("netWorth", assets);
        return out;
    }

    /**
     * Cash flow for period: prefer transactions; if none exist, fall back to legacy income/expense tables
     */
    public Map<String, Object> cashFlowForPeriod(LocalDateTime start, LocalDateTime end) {
        List<Transaction> tx = transactionService.findBetween(start, end);

        BigDecimal inflow = BigDecimal.ZERO;
        BigDecimal outflow = BigDecimal.ZERO;

        if (tx != null && !tx.isEmpty()) {
            inflow = tx.stream()
                    .filter(t -> t.getType() == TransactionType.INCOME)
                    .map(t -> t.getAmount() != null ? t.getAmount() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            outflow = tx.stream()
                    .filter(t -> t.getType() == TransactionType.EXPENSE)
                    .map(t -> t.getAmount() != null ? t.getAmount() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            // Fallback to legacy tables
            try {
                Double incSum = incomeRepository.findByDateBetween(start.toLocalDate(), end.toLocalDate()).stream()
                        .mapToDouble(i -> i.getAmount() != null ? i.getAmount() : 0.0)
                        .sum();
                inflow = BigDecimal.valueOf(incSum);
            } catch (Exception ignored) {}
            try {
                Double expSum = expenseRepository.findByDateBetween(start.toLocalDate(), end.toLocalDate()).stream()
                        .mapToDouble(e -> e.getAmount() != null ? e.getAmount() : 0.0)
                        .sum();
                outflow = BigDecimal.valueOf(expSum);
            } catch (Exception ignored) {}
        }
        Map<String, Object> r = new HashMap<>();
        r.put("inflow", inflow);
        r.put("outflow", outflow);
        r.put("surplus", inflow.subtract(outflow));
        return r;
    }
}



