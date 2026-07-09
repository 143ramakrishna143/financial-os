package com.financialos.service.widget;

import com.financialos.dto.widget.CashFlowWidgetDTO;
import com.financialos.service.FinanceFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class CashFlowWidgetService {

    private final FinanceFacade financeFacade;

    public CashFlowWidgetService(FinanceFacade financeFacade) {
        this.financeFacade = financeFacade;
    }

    /**
     * Get cash flow for last 30 days by default
     */
    public CashFlowWidgetDTO getDefault() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minus(30, ChronoUnit.DAYS);
        return getForPeriod(start, end);
    }

    public CashFlowWidgetDTO getForPeriod(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> cf = financeFacade.getCashFlow(start, end);
        CashFlowWidgetDTO dto = new CashFlowWidgetDTO();
        Object inflow = cf.get("inflow");
        Object outflow = cf.get("outflow");
        Object surplus = cf.get("surplus");
        if (inflow instanceof java.math.BigDecimal) dto.setInflow((java.math.BigDecimal) inflow);
        if (outflow instanceof java.math.BigDecimal) dto.setOutflow((java.math.BigDecimal) outflow);
        if (surplus instanceof java.math.BigDecimal) dto.setSurplus((java.math.BigDecimal) surplus);
        return dto;
    }
}

