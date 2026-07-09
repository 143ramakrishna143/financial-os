package com.financialos.service.widget;

import com.financialos.dto.widget.FinancialHealthWidgetDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FinancialHealthWidgetService {

    private final CashFlowWidgetService cashFlowWidgetService;
    private final InvestmentWidgetService investmentWidgetService;

    public FinancialHealthWidgetService(CashFlowWidgetService cashFlowWidgetService, InvestmentWidgetService investmentWidgetService) {
        this.cashFlowWidgetService = cashFlowWidgetService;
        this.investmentWidgetService = investmentWidgetService;
    }

    public FinancialHealthWidgetDTO get() {
        FinancialHealthWidgetDTO dto = new FinancialHealthWidgetDTO();
        // Simple heuristic-based scoring for MVP
        var cf = cashFlowWidgetService.getDefault();
        var inv = investmentWidgetService.get();

        BigDecimal inflow = cf.getInflow() != null ? cf.getInflow() : BigDecimal.ZERO;
        BigDecimal surplus = cf.getSurplus() != null ? cf.getSurplus() : BigDecimal.ZERO;
        double savingsRate = 0.0;
        if (inflow.compareTo(BigDecimal.ZERO) > 0) {
            savingsRate = surplus.divide(inflow, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100.0;
        }

        BigDecimal totalInvest = inv.getTotalValue() != null ? inv.getTotalValue() : BigDecimal.ZERO;
        BigDecimal assets = (inv.getTotalValue() != null ? inv.getTotalValue() : BigDecimal.ZERO).add(BigDecimal.ZERO);
        double investmentRatio = 0.0;
        if (assets.compareTo(BigDecimal.ZERO) > 0) {
            investmentRatio = totalInvest.divide(assets, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100.0;
        }

        // Emergency fund percent unknown (no user profile); set null
        dto.setEmergencyFundPercent(null);
        dto.setSavingsRatePercent(savingsRate);
        dto.setInvestmentRatioPercent(investmentRatio);

        // Score: simple weighted sum
        double score = 50.0; // base
        score += Math.min(20.0, savingsRate / 5.0); // up to +20
        score += Math.min(20.0, investmentRatio / 5.0); // up to +20
        int finalScore = (int) Math.max(0, Math.min(100, Math.round(score)));
        dto.setScore(finalScore);
        return dto;
    }
}

