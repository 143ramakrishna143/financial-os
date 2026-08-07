package com.financialos.dashboard.widget;

import com.financialos.service.widget.InvestmentWidgetService;
import org.springframework.stereotype.Component;

/**
 * Investment Portfolio Dashboard Widget.
 *
 * Displays investment portfolio including mutual funds and stocks.
 * Wraps InvestmentWidgetService and adapts response to DashboardWidgetResponse format.
 */
@Component
public class InvestmentWidget implements DashboardWidget {

    private final InvestmentWidgetService investmentWidgetService;

    public InvestmentWidget(InvestmentWidgetService investmentWidgetService) {
        this.investmentWidgetService = investmentWidgetService;
    }

    @Override
    public String getWidgetId() {
        return "investments";
    }

    @Override
    public DashboardWidgetResponse getData() {
        Object data = investmentWidgetService.get();
        return new DashboardWidgetResponse(
            getWidgetId(),
            "Investments",
            data
        );
    }
}
