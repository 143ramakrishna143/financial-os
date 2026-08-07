package com.financialos.dashboard.widget;

/**
 * Categories for organizing and filtering dashboard widgets.
 *
 * Enables dashboard customization, grouping, and filtering.
 *
 * Future Use Cases:
 * - Desktop Dashboard: Collapse/expand by category
 * - Mobile Dashboard: Show only FINANCE category
 * - AI Advisor: "Only summarize investment widgets"
 * - Custom Dashboards: User selects which categories to display
 * - Widget Search: Filter by category
 * - Performance Monitoring: Identify slow categories
 *
 * Categories:
 * - FINANCE: Core financial metrics (net worth, cash flow, health)
 * - INVESTMENTS: Investment portfolio and analysis
 * - GOALS: Financial goals and progress tracking
 * - SYSTEM: System widgets (help, documentation, settings)
 * - INSURANCE: Insurance coverage and policies
 */
public enum WidgetCategory {
    /**
     * Core financial overview and metrics.
     * Examples: Net Worth, Cash Flow, Financial Health
     */
    FINANCE("Finance"),

    /**
     * Investment portfolio and market analysis.
     * Examples: Investment Portfolio, Asset Allocation
     */
    INVESTMENTS("Investments"),

    /**
     * Financial goals and progress.
     * Examples: Goals, Milestones, Goal Progress
     */
    GOALS("Goals"),

    /**
     * System-level widgets and navigation.
     * Examples: Dashboard Help, Settings, Documentation
     */
    SYSTEM("System"),

    /**
     * Insurance and risk management.
     * Examples: Insurance Coverage, Policy Details
     */
    INSURANCE("Insurance");

    private final String displayName;

    WidgetCategory(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the human-readable display name for this category.
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Parses a string to WidgetCategory.
     * Case-insensitive.
     *
     * @param value the string to parse
     * @return WidgetCategory or null if not found
     */
    public static WidgetCategory fromString(String value) {
        if (value == null) return null;
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
