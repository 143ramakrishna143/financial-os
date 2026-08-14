package com.financialos.dashboard.widget;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Generic response object for all dashboard widgets.
 *
 * This generic DTO provides strong typing for widget responses while maintaining
 * clean JSON serialization. Each widget returns its specific data type:
 *
 * Examples:
 * <pre>
 * DashboardWidgetResponse<NetWorthWidgetDTO> netWorthResponse = ...
 * DashboardWidgetResponse<CashFlowWidgetDTO> cashFlowResponse = ...
 * DashboardWidgetResponse<List<GoalWidgetDTO>> goalsResponse = ...
 * </pre>
 *
 * Benefits of Generics:
 * - Compile-time type safety (no casting)
 * - Better IDE autocomplete and refactoring support
 * - Clear contract between widget and consumer
 * - Easier frontend integration (type-aware)
 * - Simpler OpenAPI/Swagger documentation
 *
 * @param <T> The widget-specific data type
 */
public class DashboardWidgetResponse<T> {
    @JsonProperty("widgetId")
    private String widgetId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("lastUpdated")
    private LocalDateTime lastUpdated;

    @JsonProperty("status")
    private WidgetStatus status;

    @JsonProperty("data")
    private T data;

    @JsonProperty("executionMillis")
    private long executionMillis;

    @JsonProperty("category")
    private WidgetCategory category;

    /**
     * Default constructor.
     * Initializes with current timestamp and SUCCESS status.
     */
    public DashboardWidgetResponse() {
        this.lastUpdated = LocalDateTime.now();
        this.status = WidgetStatus.SUCCESS;
        this.executionMillis = 0;
    }

    /**
     * Convenience constructor for creating successful responses.
     *
     * @param widgetId the widget identifier
     * @param title the widget title
     * @param data the widget-specific data
     */
    public DashboardWidgetResponse(String widgetId, String title, T data) {
        this();
        this.widgetId = widgetId;
        this.title = title;
        this.data = data;
    }

    /**
     * Constructor for creating error responses.
     *
     * @param widgetId the widget identifier
     * @param status the widget status (typically FAILED or EMPTY)
     * @param message error message or reason
     */
    public DashboardWidgetResponse(String widgetId, WidgetStatus status, String message) {
        this.widgetId = widgetId;
        this.lastUpdated = LocalDateTime.now();
        this.status = status;
        this.data = (T) message;
        this.title = widgetId;
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

    public WidgetStatus getStatus() {
        return status;
    }

    public void setStatus(WidgetStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getExecutionMillis() {
        return executionMillis;
    }

    public void setExecutionMillis(long executionMillis) {
        this.executionMillis = executionMillis;
    }

    public WidgetCategory getCategory() {
        return category;
    }

    public void setCategory(WidgetCategory category) {
        this.category = category;
    }

    /**
     * Checks if the response represents a successful execution.
     *
     * @return true if status is SUCCESS
     */
    public boolean isSuccessful() {
        return status == WidgetStatus.SUCCESS;
    }

    /**
     * Checks if the response represents a failed execution.
     *
     * @return true if status is FAILED
     */
    public boolean isFailed() {
        return status == WidgetStatus.FAILED;
    }
}
