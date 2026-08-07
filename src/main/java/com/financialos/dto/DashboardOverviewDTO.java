package com.financialos.dto;

import com.financialos.dashboard.widget.DashboardWidgetResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Unified dashboard overview containing all widget responses.
 *
 * This DTO aggregates responses from all registered dashboard widgets.
 * Each widget is identified by its unique ID and provides response data
 * in the standardized DashboardWidgetResponse format.
 *
 * Widget placeholders (expected keys):
 * - "net-worth": Net worth calculation widget
 * - "cash-flow": Cash flow analysis widget
 * - "investments": Investment portfolio widget
 * - "goals": Financial goals widget
 * - "health": Financial health indicator widget
 * - "timeline": Timeline or trend analysis widget
 * - "recent-transactions": Recent transaction history widget
 * - "upcoming-bills": Upcoming bills/payments widget
 *
 * The widgetResponses map is extensible - new widgets can be added
 * without modifying this DTO or the dashboard service.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardOverviewDTO {
    private Map<String, DashboardWidgetResponse> widgetResponses;
    private LocalDateTime generatedAt;

    public DashboardOverviewDTO() {
        this.widgetResponses = new HashMap<>();
        this.generatedAt = LocalDateTime.now();
    }

    public Map<String, DashboardWidgetResponse> getWidgetResponses() {
        return widgetResponses;
    }

    public void setWidgetResponses(Map<String, DashboardWidgetResponse> widgetResponses) {
        this.widgetResponses = widgetResponses;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    /**
     * Convenience method to get a specific widget response by ID.
     *
     * @param widgetId the ID of the widget to retrieve
     * @return the DashboardWidgetResponse or null if not found
     */
    public DashboardWidgetResponse getWidget(String widgetId) {
        return widgetResponses.get(widgetId);
    }

    /**
     * Convenience method to add a widget response.
     *
     * @param widgetId the ID of the widget
     * @param response the DashboardWidgetResponse
     */
    public void addWidget(String widgetId, DashboardWidgetResponse response) {
        widgetResponses.put(widgetId, response);
    }
}

