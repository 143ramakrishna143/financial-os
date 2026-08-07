package com.financialos.dashboard.widget;

import com.financialos.service.widget.NetWorthWidgetService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Net Worth Dashboard Widget.
 *
 * Displays total net worth calculation including assets and liabilities.
 * Wraps NetWorthWidgetService and adapts response to DashboardWidgetResponse format.
 */
@Component
@Order(1)
public class NetWorthWidget implements DashboardWidget {

    private final NetWorthWidgetService netWorthWidgetService;

    public NetWorthWidget(NetWorthWidgetService netWorthWidgetService) {
        this.netWorthWidgetService = netWorthWidgetService;
    }

    @Override
    public String getWidgetId() {
        return WidgetIds.NET_WORTH;
    }

    @Override
    public String getTitle() {
        return "Net Worth";
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public WidgetCategory getCategory() {
        return WidgetCategory.FINANCE;
    }

    @Override
    public DashboardWidgetResponse<?> getData() {
        Object data = netWorthWidgetService.get();
        return new DashboardWidgetResponse<>(getWidgetId(), getTitle(), data);
    }
}

