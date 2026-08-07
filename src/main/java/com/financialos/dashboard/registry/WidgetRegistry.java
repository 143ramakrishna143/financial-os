package com.financialos.dashboard.registry;

import com.financialos.dashboard.widget.DashboardWidget;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Central registry for all dashboard widgets.
 *
 * Responsibilities:
 * - Collect and register all available widgets
 * - Provide access to widgets by ID
 * - Return all registered widgets
 * - Enable future widgets without modifying DashboardService
 *
 * Design Pattern: Service Locator
 * This allows new widgets to be added by simply:
 * 1. Implementing DashboardWidget interface
 * 2. Marking as @Component (Spring will auto-register)
 * 3. No changes needed to WidgetRegistry or DashboardService
 */
@Component
public class WidgetRegistry {
    private final Map<String, DashboardWidget> widgets = new LinkedHashMap<>();

    public WidgetRegistry(List<DashboardWidget> widgetList) {
        for (DashboardWidget widget : widgetList) {
            registerWidget(widget);
        }
    }

    /**
     * Registers a widget in the registry.
     * If a widget with the same ID already exists, it will be replaced.
     *
     * @param widget the DashboardWidget to register
     */
    public void registerWidget(DashboardWidget widget) {
        widgets.put(widget.getWidgetId(), widget);
    }

    /**
     * Retrieves a widget by its ID.
     *
     * @param widgetId the unique widget identifier
     * @return the DashboardWidget if found, Optional.empty() otherwise
     */
    public Optional<DashboardWidget> getWidget(String widgetId) {
        return Optional.ofNullable(widgets.get(widgetId));
    }

    /**
     * Returns all registered widgets in insertion order.
     *
     * @return list of all registered DashboardWidgets
     */
    public List<DashboardWidget> getAllWidgets() {
        return new ArrayList<>(widgets.values());
    }

    /**
     * Returns the IDs of all registered widgets.
     *
     * @return set of all widget IDs
     */
    public Set<String> getWidgetIds() {
        return new HashSet<>(widgets.keySet());
    }

    /**
     * Checks if a widget is registered.
     *
     * @param widgetId the widget ID to check
     * @return true if the widget is registered, false otherwise
     */
    public boolean hasWidget(String widgetId) {
        return widgets.containsKey(widgetId);
    }

    /**
     * Returns the total number of registered widgets.
     *
     * @return count of registered widgets
     */
    public int getWidgetCount() {
        return widgets.size();
    }
}
