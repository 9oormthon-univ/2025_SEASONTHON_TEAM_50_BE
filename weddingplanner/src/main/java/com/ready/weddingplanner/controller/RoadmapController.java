package com.ready.weddingplanner.controller;

import com.ready.weddingplanner.domain.RoadmapTemplate;
import com.ready.weddingplanner.domain.UserTask;
import com.ready.weddingplanner.dto.RoadmapDto;
import com.ready.weddingplanner.service.RoadmapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roadmap")
@RequiredArgsConstructor
public class RoadmapController {

    private final RoadmapService roadmapService;

    @GetMapping("/templates")
    public ResponseEntity<List<RoadmapDto.RoadmapTemplateResponse>> getRoadmapTemplates() {
        List<RoadmapTemplate> templates = roadmapService.getRoadmapTemplates();
        List<RoadmapDto.RoadmapTemplateResponse> response = templates.stream()
                .map(RoadmapDto.RoadmapTemplateResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/me/roadmap/instantiate")
    public ResponseEntity<RoadmapDto.InstantiateResponse> instantiateRoadmap() {
        int createdCount = roadmapService.instantiateRoadmap();
        RoadmapDto.InstantiateResponse response = new RoadmapDto.InstantiateResponse();
        response.setCreated(createdCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/tasks")
    public ResponseEntity<List<RoadmapDto.TaskResponse>> getMyTasks() {
        List<UserTask> tasks = roadmapService.getMyTasks();
        List<RoadmapDto.TaskResponse> response = tasks.stream()
                .map(RoadmapDto.TaskResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
