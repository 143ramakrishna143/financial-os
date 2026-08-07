package com.financialos.dashboard.widget;

import com.financialos.service.widget.FinancialHealthWidgetService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Financial Health Dashboard Widget.
 *
 * Displays overall financial health indicators.
 * Wraps FinancialHealthWidgetService and adapts response to DashboardWidgetResponse format.
 */
@Component
@Order(5)
public class FinancialHealthWidget implements DashboardWidget {

    private final FinancialHealthWidgetService financialHealthWidgetService;

    public FinancialHealthWidget(FinancialHealthWidgetService financialHealthWidgetService) {
        this.financialHealthWidgetService = financialHealthWidgetService;
    }

    @Override
    public String getWidgetId() {
        return WidgetIds.FINANCIAL_HEALTH;
    }

    @Override
    public String getTitle() {
        return "Financial Health";
    }

    @Override
    public int getOrder() {
        return 5;
    }

    @Override
    public DashboardWidgetResponse<?> getData() {
        Object data = financialHealthWidgetService.get();
        return new DashboardWidgetResponse<>(getWidgetId(), getTitle(), data);
    }
}
