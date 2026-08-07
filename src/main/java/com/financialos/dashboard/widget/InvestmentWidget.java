package com.financialos.dashboard.widget;

import com.financialos.service.widget.InvestmentWidgetService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Investment Portfolio Dashboard Widget.
 *
 * Displays investment portfolio including mutual funds and stocks.
 * Wraps InvestmentWidgetService and adapts response to DashboardWidgetResponse format.
 */
@Component
@Order(3)
public class InvestmentWidget implements DashboardWidget {

    private final InvestmentWidgetService investmentWidgetService;

    public InvestmentWidget(InvestmentWidgetService investmentWidgetService) {
        this.investmentWidgetService = investmentWidgetService;
    }

    @Override
    public String getWidgetId() {
        return WidgetIds.INVESTMENTS;
    }

    @Override
    public String getTitle() {
        return "Investments";
    }

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public DashboardWidgetResponse<?> getData() {
        Object data = investmentWidgetService.get();
        return new DashboardWidgetResponse<>(getWidgetId(), getTitle(), data);
    }
}
