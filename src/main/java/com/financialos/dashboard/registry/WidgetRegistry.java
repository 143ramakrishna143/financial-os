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
     * Validations performed on startup:
     * - Widget IDs must be unique (fails fast)
     * - Widget order values must be unique (fails fast)
     *
     * After initialization the internal collections are made immutable to
     * prevent runtime mutation.
     *
     * @param widgetList auto-wired list of all DashboardWidget beans
     */
    public WidgetRegistry(List<DashboardWidget> widgetList) {
        // Temporary mutable collections for validation and sorting
        Map<String, DashboardWidget> tempMap = new LinkedHashMap<>();
        List<DashboardWidget> tempList = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        Set<Integer> orders = new HashSet<>();

        for (DashboardWidget widget : widgetList) {
            // Fail fast on duplicate widget ids
            if (!ids.add(widget.getWidgetId())) {
                throw new IllegalStateException("Duplicate widget id detected during startup: " + widget.getWidgetId());
            }

            // Fail fast on duplicate order values
            if (!orders.add(widget.getOrder())) {
                throw new IllegalStateException("Duplicate widget order detected during startup: " + widget.getWidgetId() + " has order " + widget.getOrder());
            }

            tempMap.put(widget.getWidgetId(), widget);
            tempList.add(widget);
        }

        // Sort by order and make immutable
        tempList.sort(Comparator.comparingInt(DashboardWidget::getOrder));
        this.widgetsByOrder = Collections.unmodifiableList(new ArrayList<>(tempList));
        this.widgets = Collections.unmodifiableMap(new LinkedHashMap<>(tempMap));
    }

    /**
     * Registering widgets at runtime is unsupported. Widgets are auto-discovered
     * and validated during application startup.
     */
    private void registerWidget(DashboardWidget widget) {
        throw new UnsupportedOperationException("Runtime widget registration is not supported");
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
