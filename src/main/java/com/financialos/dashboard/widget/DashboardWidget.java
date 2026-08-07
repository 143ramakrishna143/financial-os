package com.financialos.dashboard.widget;

/**
 * Core interface for all dashboard widgets.
 *
 * Every widget implementing this interface:
 * - Must provide a unique widget ID
 * - Must return structured data via DashboardWidgetResponse
 * - Is independent and can be registered dynamically
 *
 * Widgets are responsible for:
 * - Calling FinanceFacade to get data
 * - Structuring response in DashboardWidgetResponse format
 * - Handling their own errors gracefully
 *
 * Widgets MUST NOT:
 * - Call other widgets
 * - Access repositories directly
 * - Contain business logic (only orchestration)
 */
public interface DashboardWidget {

    /**
     * Returns the unique identifier for this widget.
     * Used by the registry to identify and load widgets.
     *
     * @return unique widget ID (e.g., "net-worth", "cash-flow")
     */
    String getWidgetId();

    /**
     * Executes the widget and returns structured response.
     * This method is called by DashboardService.
     *
     * @return DashboardWidgetResponse containing widget data
     */
    DashboardWidgetResponse getData();
}
