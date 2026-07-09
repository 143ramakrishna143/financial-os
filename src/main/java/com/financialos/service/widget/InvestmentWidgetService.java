package com.financialos.service.widget;

import com.financialos.dto.widget.InvestmentWidgetDTO;
import com.financialos.service.MutualFundService;
import com.financialos.service.StockService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InvestmentWidgetService {

    private final MutualFundService mutualFundService;
    private final StockService stockService;

    public InvestmentWidgetService(MutualFundService mutualFundService, StockService stockService) {
        this.mutualFundService = mutualFundService;
        this.stockService = stockService;
    }

    public InvestmentWidgetDTO get() {
        InvestmentWidgetDTO dto = new InvestmentWidgetDTO();
        Double mf = 0.0;
        Double sv = 0.0;
        try { mf = mutualFundService.getTotalMutualFundCurrentValue(); } catch (Exception ignored) {}
        try { sv = stockService.getTotalStockCurrentValue(); } catch (Exception ignored) {}
        BigDecimal mfVal = BigDecimal.valueOf(mf);
        BigDecimal svVal = BigDecimal.valueOf(sv);
        BigDecimal total = mfVal.add(svVal);
        dto.setMutualFundValue(mfVal);
        dto.setStockValue(svVal);
        dto.setTotalValue(total);
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            dto.setMutualFundPercent(mfVal.divide(total, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100);
            dto.setStockPercent(svVal.divide(total, 4, java.math.RoundingMode.HALF_UP).doubleValue() * 100);
        } else {
            dto.setMutualFundPercent(0.0);
            dto.setStockPercent(0.0);
        }
        return dto;
    }
}

