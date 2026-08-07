package com.financialos.dashboard.widget;

import com.financialos.service.widget.RecentTransactionsWidgetService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Recent Transactions Dashboard Widget.
 *
 * Displays recent financial transactions.
 * Wraps RecentTransactionsWidgetService and adapts response to DashboardWidgetResponse format.
 * Default limit is 10 recent transactions.
 */
@Component
@Order(6)
public class RecentTransactionsWidget implements DashboardWidget {

    private final RecentTransactionsWidgetService recentTransactionsWidgetService;

    public RecentTransactionsWidget(RecentTransactionsWidgetService recentTransactionsWidgetService) {
        this.recentTransactionsWidgetService = recentTransactionsWidgetService;
    }

    @Override
    public String getWidgetId() {
        return WidgetIds.RECENT_TRANSACTIONS;
    }

    @Override
    public String getTitle() {
        return "Recent Transactions";
    }

    @Override
    public int getOrder() {
        return 6;
    }

    @Override
    public DashboardWidgetResponse<?> getData() {
        Object data = recentTransactionsWidgetService.getRecent(10);
        return new DashboardWidgetResponse<>(getWidgetId(), getTitle(), data);
    }
}
