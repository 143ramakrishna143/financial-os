package com.financialos.repository;

import com.financialos.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByStatus(String status);
    List<Goal> findByGoalType(String goalType);
}

