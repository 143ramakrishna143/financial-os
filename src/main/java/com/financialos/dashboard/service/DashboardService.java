package com.financialos.dashboard.service;

import com.financialos.dashboard.registry.WidgetRegistry;
import com.financialos.dashboard.widget.DashboardWidget;
import com.financialos.dashboard.widget.DashboardWidgetResponse;
import com.financialos.dto.DashboardOverviewDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Orchestrates dashboard widget execution and aggregation.
 *
 * Responsibilities:
 * - Load all registered widgets from WidgetRegistry
 * - Execute each widget independently
 * - Handle widget errors gracefully
 * - Aggregate responses into DashboardOverviewDTO
 *
 * This service MUST NOT:
 * - Contain business logic (only orchestration)
 * - Call repositories directly
 * - Modify widget responses
 * - Know about specific widget implementations
 *
 * The service is designed to be purely compositional:
 * it doesn't care what widgets are registered, only that they
 * implement DashboardWidget and return valid responses.
 */
@Service
public class DashboardService {
    private static final Logger logger = Logger.getLogger(DashboardService.class.getName());
    private final WidgetRegistry widgetRegistry;

    public DashboardService(WidgetRegistry widgetRegistry) {
        this.widgetRegistry = widgetRegistry;
    }

    /**
     * Builds a complete dashboard overview by executing all registered widgets.
     *
     * This method:
     * 1. Loads all widgets from the registry
     * 2. Executes each widget independently
     * 3. Collects responses in a unified format
     * 4. Returns aggregated DashboardOverviewDTO
     *
     * If a widget fails, its error is logged and the dashboard continues with other widgets.
     *
     * @return DashboardOverviewDTO containing all widget responses
     */
    public DashboardOverviewDTO getDashboardOverview() {
        DashboardOverviewDTO overview = new DashboardOverviewDTO();
        Map<String, DashboardWidgetResponse> widgetResponses = new HashMap<>();

        List<DashboardWidget> allWidgets = widgetRegistry.getAllWidgets();

        for (DashboardWidget widget : allWidgets) {
            try {
                DashboardWidgetResponse response = widget.getData();
                widgetResponses.put(widget.getWidgetId(), response);
                logger.log(Level.INFO, "Widget executed successfully: " + widget.getWidgetId());
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error executing widget: " + widget.getWidgetId(), e);
                DashboardWidgetResponse errorResponse = new DashboardWidgetResponse();
                errorResponse.setWidgetId(widget.getWidgetId());
                errorResponse.setStatus("error");
                errorResponse.setData("Failed to load widget: " + e.getMessage());
                widgetResponses.put(widget.getWidgetId(), errorResponse);
            }
        }

        overview.setWidgetResponses(widgetResponses);
        return overview;
    }

    /**
     * Returns the count of registered widgets.
     * Useful for monitoring dashboard configuration.
     *
     * @return number of registered widgets
     */
    public int getWidgetCount() {
        return widgetRegistry.getWidgetCount();
    }

    /**
     * Returns the IDs of all registered widgets.
     * Useful for debugging and configuration inspection.
     *
     * @return list of widget IDs
     */
    public List<String> getRegisteredWidgetIds() {
        return widgetRegistry.getWidgetIds().stream().toList();
    }
}
