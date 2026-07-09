package com.financialos.dto.widget;

import java.math.BigDecimal;

public class CashFlowWidgetDTO {
    private BigDecimal inflow;
    private BigDecimal outflow;
    private BigDecimal surplus;

    public CashFlowWidgetDTO() {}

    public BigDecimal getInflow() { return inflow; }
    public void setInflow(BigDecimal inflow) { this.inflow = inflow; }

    public BigDecimal getOutflow() { return outflow; }
    public void setOutflow(BigDecimal outflow) { this.outflow = outflow; }

    public BigDecimal getSurplus() { return surplus; }
    public void setSurplus(BigDecimal surplus) { this.surplus = surplus; }
}

