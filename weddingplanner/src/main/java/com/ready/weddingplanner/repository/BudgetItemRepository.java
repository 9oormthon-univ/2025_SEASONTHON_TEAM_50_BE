package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.BudgetItem;
import com.ready.weddingplanner.domain.BudgetPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {
    List<BudgetItem> findByBudgetPlan(BudgetPlan budgetPlan);
}
