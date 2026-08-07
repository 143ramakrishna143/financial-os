package com.financialos.dashboard.widget;

import com.financialos.service.widget.GoalWidgetService;
import org.springframework.stereotype.Component;

/**
 * Financial Goals Dashboard Widget.
 *
 * Displays financial goals and their progress.
 * Wraps GoalWidgetService and adapts response to DashboardWidgetResponse format.
 */
@Component
public class GoalsWidget implements DashboardWidget {

    private final GoalWidgetService goalWidgetService;

    public GoalsWidget(GoalWidgetService goalWidgetService) {
        this.goalWidgetService = goalWidgetService;
    }

    @Override
    public String getWidgetId() {
        return "goals";
    }

    @Override
    public DashboardWidgetResponse getData() {
        Object data = goalWidgetService.getAll();
        return new DashboardWidgetResponse(
            getWidgetId(),
            "Goals",
            data
        );
    }
}
