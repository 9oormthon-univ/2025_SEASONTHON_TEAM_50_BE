package com.ready.weddingplanner.dto;

import com.ready.weddingplanner.domain.BudgetItem;
import com.ready.weddingplanner.domain.BudgetPlan;
import lombok.Data;
import lombok.NoArgsConstructor;


public class BudgetDto {

    @Data
    @NoArgsConstructor
    public static class BudgetResponse {
        private Long planId;
        private Long targetAmount;
        private Long plannedTotal;
        private Long actualTotal;

        public BudgetResponse(BudgetPlan plan) {
            this.planId = plan.getId();
            this.targetAmount = plan.getTargetAmount();
        }
    }

    @Data
    @NoArgsConstructor
    public static class BudgetItemResponse {
        private Long id;
        private String category;
        private Long planned;
        private Long actual;

        public BudgetItemResponse(BudgetItem item) {
            this.id = item.getId();
            this.category = item.getCategory();
            this.planned = item.getPlanned();
            this.actual = item.getActual();
        }
    }
}
