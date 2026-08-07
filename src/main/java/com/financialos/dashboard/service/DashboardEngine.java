package com.financialos.dashboard.service;

import com.financialos.dashboard.registry.WidgetRegistry;
import com.financialos.dashboard.widget.DashboardWidget;
import com.financialos.dashboard.widget.DashboardWidgetResponse;
import com.financialos.dashboard.widget.WidgetStatus;
import com.financialos.dto.DashboardOverviewDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Core orchestration engine for dashboard widget execution.
 *
 * Responsibilities:
 * - Retrieve widgets from registry in defined order
 * - Execute each widget independently
 * - Handle widget execution errors gracefully
 * - Aggregate all widget responses
 * - Provide execution metrics and logging
 *
 * Future Capabilities (without modifying existing code):
 * - Parallel widget execution via CompletableFuture
 * - Widget response caching
 * - Execution metrics and monitoring
 * - Permission-based widget filtering
 * - Feature flag support
 * - Widget timeout handling
 *
 * Architecture:
 * The engine acts as pure orchestration - it has ZERO knowledge of widget
 * implementations or business logic. It only knows how to:
 * 1. Get widgets from registry
 * 2. Call their getData() method
 * 3. Handle errors
 * 4. Collect responses
 *
 * This design enables:
 * - Widget independence
 * - Testability
 * - Extensibility
 * - Future optimization
 */
@Service
public class DashboardEngine {
    private static final Logger logger = Logger.getLogger(DashboardEngine.class.getName());
    private final WidgetRegistry widgetRegistry;

    public DashboardEngine(WidgetRegistry widgetRegistry) {
        this.widgetRegistry = widgetRegistry;
    }

    /**
     * Executes all registered widgets in order and collects responses.
     *
     * Each widget is executed independently. If a widget fails:
     * - Its error is logged
     * - An error response is returned for that widget
     * - Execution continues with remaining widgets
     * - Dashboard completes with partial data
     *
     * @return List of DashboardWidgetResponse in execution order
     */
    public List<DashboardWidgetResponse<?>> executeAllWidgets() {
        List<DashboardWidgetResponse<?>> responses = new ArrayList<>();
        List<DashboardWidget> widgets = widgetRegistry.getAllWidgetsInOrder();

        for (DashboardWidget widget : widgets) {
            DashboardWidgetResponse<?> response = executeWidget(widget);
            responses.add(response);
        }

        return responses;
    }

    /**
     * Executes a single widget with error handling and execution time measurement.
     *
     * This protected method is designed for future enhancements such as:
     * - Widget response caching
     * - Execution metrics collection
     * - Permission-based access control
     * - Feature flag validation
     * - Timeout handling
     *
     * @param widget the DashboardWidget to execute
     * @return DashboardWidgetResponse with status, data/error, and execution time
     */
    protected DashboardWidgetResponse<?> executeWidget(DashboardWidget widget) {
        try {
            long startTime = System.currentTimeMillis();
            logger.log(Level.FINE, "Executing widget: " + widget.getWidgetId());

            DashboardWidgetResponse<?> response = widget.getData();

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            response.setExecutionMillis(executionTime);

            logger.log(Level.INFO, "Widget executed successfully: " + widget.getWidgetId()
                    + " (took " + executionTime + "ms)");
            return response;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error executing widget: " + widget.getWidgetId(), e);
            return createErrorResponse(widget, e);
        }
    }

    /**
     * Creates an error response for a failed widget.
     *
     * @param widget the widget that failed
     * @param exception the exception that occurred
     * @return error response with FAILED status
     */
    private DashboardWidgetResponse<?> createErrorResponse(DashboardWidget widget, Exception exception) {
        DashboardWidgetResponse<?> response = new DashboardWidgetResponse<>(
            widget.getWidgetId(),
            WidgetStatus.FAILED,
            "Failed to execute widget: " + exception.getMessage()
        );
        response.setTitle(widget.getTitle());
        return response;
    }

    /**
     * Returns the count of registered widgets.
     *
     * @return total widget count
     */
    public int getWidgetCount() {
        return widgetRegistry.getWidgetCount();
    }

    /**
     * Returns the execution order of widgets.
     *
     * @return list of widget IDs in execution order
     */
    public List<String> getWidgetOrder() {
        List<DashboardWidget> widgets = widgetRegistry.getAllWidgetsInOrder();
        List<String> order = new ArrayList<>();
        for (DashboardWidget widget : widgets) {
            order.add(widget.getWidgetId() + " (" + widget.getTitle() + ")");
        }
        return order;
    }

    /**
     * Builds a complete DashboardOverviewDTO with all widget responses.
     *
     * This is the primary method called by DashboardService to get the complete dashboard.
     *
     * @return DashboardOverviewDTO containing all widget responses in order
     */
    public DashboardOverviewDTO buildDashboardOverview() {
        DashboardOverviewDTO overview = new DashboardOverviewDTO();
        List<DashboardWidgetResponse<?>> allResponses = executeAllWidgets();
        overview.setAllWidgetResponses(allResponses);
        return overview;
    }
}
