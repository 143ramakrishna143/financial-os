package com.financialos.dashboard.widget;

/**
 * Enumeration of possible widget execution statuses.
 *
 * Used to track the state of widget execution and data availability.
 * Replaces string-based status to provide type safety.
 */
public enum WidgetStatus {
    /**
     * Widget executed successfully and data is available.
     */
    SUCCESS("success"),

    /**
     * Widget is currently loading data.
     * Useful for async or long-running operations.
     */
    LOADING("loading"),

    /**
     * Widget failed to execute due to an error.
     * Error message will be in the response data field.
     */
    FAILED("failed"),

    /**
     * Widget executed successfully but has no data to display.
     * Used for widgets with optional or conditional data.
     */
    EMPTY("empty");

    private final String displayName;

    WidgetStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name of the status.
     *
     * @return string representation for API responses
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Parses a string to WidgetStatus.
     *
     * @param value string value to parse
     * @return WidgetStatus or SUCCESS if not found
     */
    public static WidgetStatus fromString(String value) {
        for (WidgetStatus status : WidgetStatus.values()) {
            if (status.displayName.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return SUCCESS;
    }
}
