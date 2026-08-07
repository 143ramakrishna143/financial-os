package com.financialos.dashboard.widget;

import com.financialos.service.widget.NetWorthWidgetService;
import org.springframework.stereotype.Component;

/**
 * Net Worth Dashboard Widget.
 *
 * Displays total net worth calculation including assets and liabilities.
 * Wraps NetWorthWidgetService and adapts response to DashboardWidgetResponse format.
 */
@Component
public class NetWorthWidget implements DashboardWidget {

    private final NetWorthWidgetService netWorthWidgetService;

    public NetWorthWidget(NetWorthWidgetService netWorthWidgetService) {
        this.netWorthWidgetService = netWorthWidgetService;
    }

    @Override
    public String getWidgetId() {
        return "net-worth";
    }

    @Override
    public DashboardWidgetResponse getData() {
        Object data = netWorthWidgetService.get();
        return new DashboardWidgetResponse(
            getWidgetId(),
            "Net Worth",
            data
        );
    }
}
