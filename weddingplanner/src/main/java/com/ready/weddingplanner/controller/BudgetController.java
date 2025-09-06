package com.ready.weddingplanner.controller;

import com.ready.weddingplanner.domain.BudgetItem;
import com.ready.weddingplanner.domain.BudgetPlan;
import com.ready.weddingplanner.dto.BudgetDto;
import com.ready.weddingplanner.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/me/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping
    public ResponseEntity<BudgetDto.BudgetResponse> getBudget() {
        BudgetPlan budgetPlan = budgetService.getBudget();
        List<BudgetItem> budgetItems = budgetService.getBudgetItemsForPlan(budgetPlan);

        long plannedTotal = budgetItems.stream().mapToLong(BudgetItem::getPlanned).sum();
        long actualTotal = budgetItems.stream().mapToLong(BudgetItem::getActual).sum();

        BudgetDto.BudgetResponse response = new BudgetDto.BudgetResponse(budgetPlan);
        response.setPlannedTotal(plannedTotal);
        response.setActualTotal(actualTotal);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/items")
    public ResponseEntity<List<BudgetDto.BudgetItemResponse>> getBudgetItems() {
        List<BudgetItem> items = budgetService.getBudgetItems();
        List<BudgetDto.BudgetItemResponse> response = items.stream()
                .map(BudgetDto.BudgetItemResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
