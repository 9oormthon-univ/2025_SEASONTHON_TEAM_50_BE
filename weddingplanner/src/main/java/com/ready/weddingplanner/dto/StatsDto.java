package com.ready.weddingplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {
    private RomanceStats romance;
    private TimeStats time;
    private BudgetStats budget;
    private TaskStats tasks;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RomanceStats {
        private long boardCount;
        private long itemCount;
        private long likes;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeStats {
        private LocalDate weddingDate;
        private long daysLeft;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BudgetStats {
        private long target;
        private long planned;
        private long actual;
        private double progress;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskStats {
        private long todo;
        private long doing;
        private long done;
        private double completionRate;
    }
}
