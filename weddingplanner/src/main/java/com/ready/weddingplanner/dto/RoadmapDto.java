package com.ready.weddingplanner.dto;

import com.ready.weddingplanner.domain.RoadmapTemplate;
import com.ready.weddingplanner.domain.UserTask;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class RoadmapDto {

    @Data
    @NoArgsConstructor
    public static class RoadmapTemplateResponse {
        private Long id;
        private String title;
        private int dueRelativeMonth;
        private String category;

        public RoadmapTemplateResponse(RoadmapTemplate template) {
            this.id = template.getId();
            this.title = template.getTitle();
            this.dueRelativeMonth = template.getDueRelativeMonth();
            this.category = template.getCategory();
        }
    }

    @Data
    @NoArgsConstructor
    public static class InstantiateResponse {
        private int created;
    }

    @Data
    @NoArgsConstructor
    public static class TaskResponse {
        private Long id;
        private String title;
        private LocalDate dueDate;
        private UserTask.TaskStatus status;
        private String category;

        public TaskResponse(UserTask task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.dueDate = task.getDueDate();
            this.status = task.getStatus();
            this.category = task.getCategory();
        }
    }
}
