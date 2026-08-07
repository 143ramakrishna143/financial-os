package com.financialos.dashboard.widget;

import com.financialos.service.widget.FinancialHealthWidgetService;
import org.springframework.stereotype.Component;

/**
 * Financial Health Dashboard Widget.
 *
 * Displays overall financial health indicators.
 * Wraps FinancialHealthWidgetService and adapts response to DashboardWidgetResponse format.
 */
@Component
public class FinancialHealthWidget implements DashboardWidget {

    private final FinancialHealthWidgetService financialHealthWidgetService;

    public FinancialHealthWidget(FinancialHealthWidgetService financialHealthWidgetService) {
        this.financialHealthWidgetService = financialHealthWidgetService;
    }

    @Override
    public String getWidgetId() {
        return "health";
    }

    @Override
    public DashboardWidgetResponse getData() {
        Object data = financialHealthWidgetService.get();
        return new DashboardWidgetResponse(
            getWidgetId(),
            "Financial Health",
            data
        );
    }
}
