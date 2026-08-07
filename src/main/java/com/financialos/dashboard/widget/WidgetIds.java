package com.financialos.dashboard.widget;

/**
 * Constants for all dashboard widget identifiers.
 *
 * Centralizes widget ID definitions to prevent hardcoding.
 * Enables compile-time safety when referencing widgets.
 *
 * Usage:
 * <pre>
 * String widgetId = WidgetIds.NET_WORTH;  // "net-worth"
 * if (response.getWidgetId().equals(WidgetIds.CASH_FLOW)) { ... }
 * </pre>
 */
public final class WidgetIds {

    /**
     * Net Worth dashboard widget.
     * Displays total net worth including assets and liabilities.
     */
    public static final String NET_WORTH = "net-worth";

    /**
     * Cash Flow dashboard widget.
     * Displays income vs expenses over a time period.
     */
    public static final String CASH_FLOW = "cash-flow";

    /**
     * Investments dashboard widget.
     * Displays investment portfolio (mutual funds, stocks).
     */
    public static final String INVESTMENTS = "investments";

    /**
     * Financial Goals dashboard widget.
     * Displays user-defined financial goals and progress.
     */
    public static final String GOALS = "goals";

    /**
     * Financial Health dashboard widget.
     * Displays overall financial health indicators and score.
     */
    public static final String FINANCIAL_HEALTH = "health";

    /**
     * Recent Transactions dashboard widget.
     * Displays recent financial transactions.
     */
    public static final String RECENT_TRANSACTIONS = "recent-transactions";

    /**
     * Timeline dashboard widget.
     * Displays historical trends and future projections.
     */
    public static final String TIMELINE = "timeline";

    /**
     * Upcoming Bills dashboard widget.
     * Displays upcoming payments and obligations.
     */
    public static final String UPCOMING_BILLS = "upcoming-bills";

    private WidgetIds() {
        // Private constructor to prevent instantiation
    }
}
