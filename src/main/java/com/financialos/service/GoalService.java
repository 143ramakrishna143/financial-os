package com.financialos.service;

import com.financialos.model.Goal;
import com.financialos.repository.GoalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepository.findById(id);
    }

    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }

    public List<Goal> getGoalsByStatus(String status) {
        return goalRepository.findByStatus(status);
    }

    public List<Goal> getGoalsByType(String goalType) {
        return goalRepository.findByGoalType(goalType);
    }

    public Double getTotalGoalTargetAmount() {
        return goalRepository.findAll().stream()
                .mapToDouble(Goal::getTargetAmount)
                .sum();
    }

    public Double getTotalGoalCurrentAmount() {
        return goalRepository.findAll().stream()
                .mapToDouble(Goal::getCurrentAmount)
                .sum();
    }
}

