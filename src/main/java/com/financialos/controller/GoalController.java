package com.financialos.controller;

import com.financialos.model.Goal;
import com.financialos.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        return ResponseEntity.ok(goalService.getAllGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        Optional<Goal> goal = goalService.getGoalById(id);
        return goal.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Goal created = goalService.saveGoal(goal);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        Optional<Goal> existing = goalService.getGoalById(id);
        if (existing.isPresent()) {
            goal.setId(id);
            Goal updated = goalService.saveGoal(goal);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Goal>> getGoalsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(goalService.getGoalsByStatus(status));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Goal>> getGoalsByType(@PathVariable String type) {
        return ResponseEntity.ok(goalService.getGoalsByType(type));
    }

    @GetMapping("/summary")
    public ResponseEntity<GoalSummary> getGoalSummary() {
        GoalSummary summary = new GoalSummary(
                goalService.getTotalGoalTargetAmount(),
                goalService.getTotalGoalCurrentAmount()
        );
        return ResponseEntity.ok(summary);
    }

    public static class GoalSummary {
        public Double totalTargetAmount;
        public Double totalCurrentAmount;

        public GoalSummary(Double totalTargetAmount, Double totalCurrentAmount) {
            this.totalTargetAmount = totalTargetAmount;
            this.totalCurrentAmount = totalCurrentAmount;
        }
    }
}

