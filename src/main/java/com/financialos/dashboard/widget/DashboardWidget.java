package com.financialos.dashboard.widget;

/**
 * Core interface for all dashboard widgets.
 *
 * Every widget implementing this interface:
 * - Must provide a unique widget ID (see {@link WidgetIds})
 * - Must provide a user-friendly title
 * - Must define its display order
 * - Must return structured data via DashboardWidgetResponse
 *
 * Widgets are responsible for:
 * - Calling FinanceFacade to get data
 * - Structuring response in DashboardWidgetResponse<T> format
 * - Handling their own errors gracefully
 * - Exposing metadata (title, order) for dashboard layout
 *
 * Widgets MUST NOT:
 * - Call other widgets
 * - Access repositories directly
 * - Contain business logic (only orchestration)
 * - Depend on dashboard architecture
 *
 * Implementation example:
 * <pre>
 * @Component
 * @Order(1)
 * public class NetWorthWidget implements DashboardWidget {
 *     @Override
 *     public String getWidgetId() { return WidgetIds.NET_WORTH; }
 *
 *     @Override
 *     public String getTitle() { return "Net Worth"; }
 *
 *     @Override
 *     public int getOrder() { return 1; }
 *
 *     @Override
 *     public DashboardWidgetResponse<?> getData() { ... }
 * }
 * </pre>
 */
public interface DashboardWidget {

    /**
     * Returns the unique identifier for this widget.
     * Use constants from {@link WidgetIds} to avoid hardcoding.
     *
     * @return unique widget ID (e.g., WidgetIds.NET_WORTH)
     */
    String getWidgetId();

    /**
     * Returns the user-friendly title for this widget.
     * Used by dashboard UI for display and OpenAPI documentation.
     *
     * @return widget title (e.g., "Net Worth")
     */
    String getTitle();

    /**
     * Returns the display order for this widget.
     * Lower values appear first on the dashboard.
     * Used by DashboardEngine to sort widgets.
     *
     * @return order value (e.g., 1, 2, 3, etc.)
     */
    int getOrder();

    /**
     * Executes the widget and returns structured response.
     * This method is called by DashboardEngine during dashboard composition.
     *
     * @return DashboardWidgetResponse with generic typing for type safety
     */
    DashboardWidgetResponse<?> getData();
}
