package com.financialos.dashboard.registry;

import com.financialos.dashboard.widget.DashboardWidget;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Central registry for all dashboard widgets.
 *
 * Responsibilities:
 * - Auto-discovers all @Component DashboardWidget beans
 * - Manages widget registration and access
 * - Preserves widget execution order via @Order annotation
 * - Enables future widgets without modifying other components
 *
 * Design Pattern: Service Locator + Registry
 *
 * Auto-Discovery Process:
 * 1. Spring finds all beans implementing DashboardWidget
 * 2. Constructor receives List<DashboardWidget> (auto-wired by Spring)
 * 3. Widgets are automatically registered in their @Order value
 * 4. No manual registration needed
 *
 * Adding a new widget requires ONLY:
 * 1. Implement DashboardWidget interface
 * 2. Add @Component and @Order(n) annotations
 * 3. No changes to registry, service, or controller
 *
 * Future Enhancements (without modifying registry):
 * - Conditional widget loading via @ConditionalOnProperty
 * - Widget lazy loading
 * - Dynamic widget enabling/disabling
 * - Widget metadata caching
 */
@Component
public class WidgetRegistry {
    private final Map<String, DashboardWidget> widgets;
    private final List<DashboardWidget> widgetsByOrder;

    /**
     * Constructor that receives all auto-discovered DashboardWidget beans.
     * Spring automatically provides all beans implementing DashboardWidget.
     *
     * @param widgetList auto-wired list of all DashboardWidget beans
     */
    public WidgetRegistry(List<DashboardWidget> widgetList) {
        this.widgets = new LinkedHashMap<>();
        this.widgetsByOrder = new ArrayList<>();

        // Register all widgets and sort by order
        for (DashboardWidget widget : widgetList) {
            registerWidget(widget);
        }

        // Sort by order annotation
        widgetsByOrder.sort(Comparator.comparingInt(DashboardWidget::getOrder));
    }

    /**
     * Registers a widget in the registry.
     *
     * @param widget the DashboardWidget to register
     */
    public void registerWidget(DashboardWidget widget) {
        widgets.put(widget.getWidgetId(), widget);
        widgetsByOrder.add(widget);
    }

    /**
     * Retrieves a widget by its ID.
     *
     * @param widgetId the unique widget identifier
     * @return Optional containing the widget if found
     */
    public Optional<DashboardWidget> getWidget(String widgetId) {
        return Optional.ofNullable(widgets.get(widgetId));
    }

    /**
     * Returns all registered widgets in insertion order.
     *
     * @return list of all registered DashboardWidgets (insertion order)
     */
    public List<DashboardWidget> getAllWidgets() {
        return new ArrayList<>(widgets.values());
    }

    /**
     * Returns all registered widgets in execution order (sorted by @Order).
     *
     * This is the preferred method for execution since it respects widget ordering.
     *
     * @return list of all registered DashboardWidgets (sorted by order)
     */
    public List<DashboardWidget> getAllWidgetsInOrder() {
        return new ArrayList<>(widgetsByOrder);
    }

    /**
     * Returns the IDs of all registered widgets in order.
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

    /**
     * Returns widget metadata for documentation purposes.
     *
     * @return map of widget ID to title
     */
    public Map<String, String> getWidgetMetadata() {
        return widgetsByOrder.stream()
            .collect(Collectors.toMap(
                DashboardWidget::getWidgetId,
                DashboardWidget::getTitle,
                (a, b) -> a,
                LinkedHashMap::new
            ));
    }
}
