package com.financialos.dto.widget;

import java.math.BigDecimal;

public class InvestmentWidgetDTO {
    private BigDecimal mutualFundValue;
    private BigDecimal stockValue;
    private BigDecimal totalValue;
    private Double mutualFundPercent;
    private Double stockPercent;

    public InvestmentWidgetDTO() {}

    public BigDecimal getMutualFundValue() { return mutualFundValue; }
    public void setMutualFundValue(BigDecimal mutualFundValue) { this.mutualFundValue = mutualFundValue; }

    public BigDecimal getStockValue() { return stockValue; }
    public void setStockValue(BigDecimal stockValue) { this.stockValue = stockValue; }

    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }

    public Double getMutualFundPercent() { return mutualFundPercent; }
    public void setMutualFundPercent(Double mutualFundPercent) { this.mutualFundPercent = mutualFundPercent; }

    public Double getStockPercent() { return stockPercent; }
    public void setStockPercent(Double stockPercent) { this.stockPercent = stockPercent; }
}

