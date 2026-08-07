package com.financialos.dashboard.widget;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Generic response object for all dashboard widgets.
 *
 * This DTO is designed to be reusable across all widget types,
 * allowing each widget to return structured data in a consistent format.
 *
 * Fields:
 * - widgetId: Unique identifier for the widget
 * - title: User-friendly widget title
 * - lastUpdated: Timestamp of when data was last fetched/calculated
 * - status: Status indicator (e.g., "success", "loading", "error")
 * - data: Widget-specific data (can be any object, widget determines structure)
 */
public class DashboardWidgetResponse {
    private String widgetId;
    private String title;
    private LocalDateTime lastUpdated;
    private String status;
    private Object data;

    public DashboardWidgetResponse() {
        this.lastUpdated = LocalDateTime.now();
        this.status = "success";
    }

    public DashboardWidgetResponse(String widgetId, String title, Object data) {
        this();
        this.widgetId = widgetId;
        this.title = title;
        this.data = data;
    }

    // Getters and Setters
    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
