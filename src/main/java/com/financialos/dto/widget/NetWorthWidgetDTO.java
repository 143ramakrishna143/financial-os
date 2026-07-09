package com.financialos.dto.widget;

import java.math.BigDecimal;

public class NetWorthWidgetDTO {
    private BigDecimal totalBalance;
    private BigDecimal mutualFundValue;
    private BigDecimal stockValue;
    private BigDecimal assets;
    private BigDecimal netWorth;

    public NetWorthWidgetDTO() {}

    public BigDecimal getTotalBalance() { return totalBalance; }
    public void setTotalBalance(BigDecimal totalBalance) { this.totalBalance = totalBalance; }

    public BigDecimal getMutualFundValue() { return mutualFundValue; }
    public void setMutualFundValue(BigDecimal mutualFundValue) { this.mutualFundValue = mutualFundValue; }

    public BigDecimal getStockValue() { return stockValue; }
    public void setStockValue(BigDecimal stockValue) { this.stockValue = stockValue; }

    public BigDecimal getAssets() { return assets; }
    public void setAssets(BigDecimal assets) { this.assets = assets; }

    public BigDecimal getNetWorth() { return netWorth; }
    public void setNetWorth(BigDecimal netWorth) { this.netWorth = netWorth; }
}

