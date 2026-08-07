package com.financialos.dashboard.widget;

import com.financialos.service.widget.RecentTransactionsWidgetService;
import org.springframework.stereotype.Component;

/**
 * Recent Transactions Dashboard Widget.
 *
 * Displays recent financial transactions.
 * Wraps RecentTransactionsWidgetService and adapts response to DashboardWidgetResponse format.
 * Default limit is 10 recent transactions.
 */
@Component
public class RecentTransactionsWidget implements DashboardWidget {

    private final RecentTransactionsWidgetService recentTransactionsWidgetService;

    public RecentTransactionsWidget(RecentTransactionsWidgetService recentTransactionsWidgetService) {
        this.recentTransactionsWidgetService = recentTransactionsWidgetService;
    }

    @Override
    public String getWidgetId() {
        return "recent-transactions";
    }

    @Override
    public DashboardWidgetResponse getData() {
        Object data = recentTransactionsWidgetService.getRecent(10);
        return new DashboardWidgetResponse(
            getWidgetId(),
            "Recent Transactions",
            data
        );
    }
}
