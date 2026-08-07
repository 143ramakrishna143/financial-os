package com.financialos.dashboard.widget;

import com.financialos.service.widget.GoalWidgetService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Financial Goals Dashboard Widget.
 *
 * Displays financial goals and their progress.
 * Wraps GoalWidgetService and adapts response to DashboardWidgetResponse format.
 */
@Component
@Order(4)
public class GoalsWidget implements DashboardWidget {

    private final GoalWidgetService goalWidgetService;

    public GoalsWidget(GoalWidgetService goalWidgetService) {
        this.goalWidgetService = goalWidgetService;
    }

    @Override
    public String getWidgetId() {
        return WidgetIds.GOALS;
    }

    @Override
    public String getTitle() {
        return "Goals";
    }

    @Override
    public int getOrder() {
        return 4;
    }

    @Override
    public WidgetCategory getCategory() {
        return WidgetCategory.GOALS;
    }

    @Override
    public DashboardWidgetResponse<?> getData() {
        Object data = goalWidgetService.getAll();
        return new DashboardWidgetResponse<>(getWidgetId(), getTitle(), data);
    }
}
