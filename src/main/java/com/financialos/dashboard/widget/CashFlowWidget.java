package com.financialos.dashboard.widget;

import com.financialos.service.widget.CashFlowWidgetService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Cash Flow Dashboard Widget.
 *
 * Displays cash flow analysis for a time period.
 * Wraps CashFlowWidgetService and adapts response to DashboardWidgetResponse format.
 * Default period is last 30 days from today.
 */
@Component
public class CashFlowWidget implements DashboardWidget {

    private final CashFlowWidgetService cashFlowWidgetService;

    public CashFlowWidget(CashFlowWidgetService cashFlowWidgetService) {
        this.cashFlowWidgetService = cashFlowWidgetService;
    }

    @Override
    public String getWidgetId() {
        return "cash-flow";
    }

    @Override
    public DashboardWidgetResponse getData() {
        LocalDateTime start = LocalDateTime.now().minusDays(30);
        LocalDateTime end = LocalDateTime.now();
        Object data = cashFlowWidgetService.getForPeriod(start, end);
        return new DashboardWidgetResponse(
            getWidgetId(),
            "Cash Flow",
            data
        );
    }
}
