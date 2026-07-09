package com.financialos.service.widget;

import com.financialos.dto.widget.GoalWidgetDTO;
import com.financialos.model.Goal;
import com.financialos.service.GoalService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalWidgetService {

    private final GoalService goalService;

    public GoalWidgetService(GoalService goalService) {
        this.goalService = goalService;
    }

    public List<GoalWidgetDTO> getAll() {
        List<Goal> goals = goalService.getAllGoals();
        return goals.stream().map(this::toDto).collect(Collectors.toList());
    }

    private GoalWidgetDTO toDto(Goal g) {
        GoalWidgetDTO dto = new GoalWidgetDTO();
        dto.setId(g.getId());
        dto.setName(g.getGoalName());
        dto.setTargetAmount(BigDecimal.valueOf(g.getTargetAmount()));
        dto.setCurrentAmount(BigDecimal.valueOf(g.getCurrentAmount()));
        double pct = 0.0;
        if (g.getTargetAmount() != null && g.getTargetAmount() > 0) {
            pct = (g.getCurrentAmount() / g.getTargetAmount()) * 100.0;
        }
        dto.setProgressPercent(pct);
        // monthsRemaining left as null for now
        return dto;
    }
}

