package com.ready.weddingplanner.controller;

import com.ready.weddingplanner.dto.StatsDto;
import com.ready.weddingplanner.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public ResponseEntity<StatsDto> getMyStats() {
        return ResponseEntity.ok(statsService.getMyStats());
    }
}
