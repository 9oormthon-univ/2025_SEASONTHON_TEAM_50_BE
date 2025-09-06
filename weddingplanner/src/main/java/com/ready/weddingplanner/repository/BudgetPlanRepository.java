package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.BudgetPlan;
import com.ready.weddingplanner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetPlanRepository extends JpaRepository<BudgetPlan, Long> {
    Optional<BudgetPlan> findByUser(User user);
}
