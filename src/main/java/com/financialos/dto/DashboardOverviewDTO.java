package com.financialos.dto;

import com.financialos.dashboard.widget.DashboardWidgetResponse;
import com.financialos.dashboard.widget.WidgetIds;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Strongly typed dashboard overview containing all widget responses.
 *
 * This DTO aggregates responses from all dashboard widgets using specific fields
 * rather than a generic map. This provides:
 *
 * Benefits:
 * - Type safety: getNetWorth() returns DashboardWidgetResponse<?> not Object
 * - API clarity: IDE autocompletion shows all available widgets
 * - OpenAPI generation: Swagger/Springdoc can document the structure
 * - Frontend integration: Type-aware languages (TypeScript) can generate DTOs
 * - JavaFX binding: Direct property binding without casting
 * - Backward compatibility: JSON structure unchanged
 *
 * Reserved Widget IDs:
 * - net-worth: Net worth calculation (assets - liabilities)
 * - cash-flow: Income vs expenses analysis
 * - investments: Investment portfolio (mutual funds, stocks)
 * - goals: Financial goals and progress
 * - health: Financial health indicators
 * - recent-transactions: Recent transaction history
 * - timeline: Historical trends and projections
 * - upcoming-bills: Upcoming payments
 *
 * Future widgets can be added as new fields without breaking existing API.
 * All fields are optional (@JsonInclude(NON_NULL)).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardOverviewDTO {

    @JsonProperty("netWorth")
    private DashboardWidgetResponse<?> netWorth;

    @JsonProperty("cashFlow")
    private DashboardWidgetResponse<?> cashFlow;

    @JsonProperty("investments")
    private DashboardWidgetResponse<?> investments;

    @JsonProperty("goals")
    private DashboardWidgetResponse<?> goals;

    @JsonProperty("financialHealth")
    private DashboardWidgetResponse<?> financialHealth;

    @JsonProperty("recentTransactions")
    private DashboardWidgetResponse<?> recentTransactions;

    @JsonProperty("timeline")
    private DashboardWidgetResponse<?> timeline;

    @JsonProperty("upcomingBills")
    private DashboardWidgetResponse<?> upcomingBills;

    @JsonProperty("generatedAt")
    private LocalDateTime generatedAt;

    /**
     * Default constructor.
     * Initializes with current timestamp.
     */
    public DashboardOverviewDTO() {
        this.generatedAt = LocalDateTime.now();
    }

    /**
     * Populates dashboard fields from a list of widget responses.
     *
     * This method takes responses from DashboardEngine and maps them to
     * the appropriate strongly-typed fields based on widget ID.
     *
     * @param responses list of widget responses in execution order
     */
    public void setAllWidgetResponses(List<DashboardWidgetResponse<?>> responses) {
        for (DashboardWidgetResponse<?> response : responses) {
            mapResponseToField(response);
        }
    }

    /**
     * Maps a widget response to its corresponding field based on widget ID.
     *
     * @param response the widget response to map
     */
    private void mapResponseToField(DashboardWidgetResponse<?> response) {
        if (response == null) return;

        switch (response.getWidgetId()) {
            case WidgetIds.NET_WORTH:
                this.netWorth = response;
                break;
            case WidgetIds.CASH_FLOW:
                this.cashFlow = response;
                break;
            case WidgetIds.INVESTMENTS:
                this.investments = response;
                break;
            case WidgetIds.GOALS:
                this.goals = response;
                break;
            case WidgetIds.FINANCIAL_HEALTH:
                this.financialHealth = response;
                break;
            case WidgetIds.RECENT_TRANSACTIONS:
                this.recentTransactions = response;
                break;
            case WidgetIds.TIMELINE:
                this.timeline = response;
                break;
            case WidgetIds.UPCOMING_BILLS:
                this.upcomingBills = response;
                break;
        }
    }

    // Getters and Setters for all fields

    public DashboardWidgetResponse<?> getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(DashboardWidgetResponse<?> netWorth) {
        this.netWorth = netWorth;
    }

    public DashboardWidgetResponse<?> getCashFlow() {
        return cashFlow;
    }

    public void setCashFlow(DashboardWidgetResponse<?> cashFlow) {
        this.cashFlow = cashFlow;
    }

    public DashboardWidgetResponse<?> getInvestments() {
        return investments;
    }

    public void setInvestments(DashboardWidgetResponse<?> investments) {
        this.investments = investments;
    }

    public DashboardWidgetResponse<?> getGoals() {
        return goals;
    }

    public void setGoals(DashboardWidgetResponse<?> goals) {
        this.goals = goals;
    }

    public DashboardWidgetResponse<?> getFinancialHealth() {
        return financialHealth;
    }

    public void setFinancialHealth(DashboardWidgetResponse<?> financialHealth) {
        this.financialHealth = financialHealth;
    }

    public DashboardWidgetResponse<?> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(DashboardWidgetResponse<?> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }

    public DashboardWidgetResponse<?> getTimeline() {
        return timeline;
    }

    public void setTimeline(DashboardWidgetResponse<?> timeline) {
        this.timeline = timeline;
    }

    public DashboardWidgetResponse<?> getUpcomingBills() {
        return upcomingBills;
    }

    public void setUpcomingBills(DashboardWidgetResponse<?> upcomingBills) {
        this.upcomingBills = upcomingBills;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    /**
     * Convenience method to check if a widget response is available.
     *
     * @param widgetId the widget ID to check
     * @return true if the widget response exists and is successful
     */
    public boolean hasWidget(String widgetId) {
        DashboardWidgetResponse<?> response = getWidgetResponse(widgetId);
        return response != null && response.isSuccessful();
    }

    /**
     * Convenience method to get a widget response by ID.
     *
     * @param widgetId the widget ID to retrieve
     * @return the DashboardWidgetResponse or null if not found
     */
    public DashboardWidgetResponse<?> getWidgetResponse(String widgetId) {
        return switch (widgetId) {
            case WidgetIds.NET_WORTH -> netWorth;
            case WidgetIds.CASH_FLOW -> cashFlow;
            case WidgetIds.INVESTMENTS -> investments;
            case WidgetIds.GOALS -> goals;
            case WidgetIds.FINANCIAL_HEALTH -> financialHealth;
            case WidgetIds.RECENT_TRANSACTIONS -> recentTransactions;
            case WidgetIds.TIMELINE -> timeline;
            case WidgetIds.UPCOMING_BILLS -> upcomingBills;
            default -> null;
        };
    }
}
