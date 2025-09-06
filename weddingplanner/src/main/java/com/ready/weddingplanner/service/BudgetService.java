package com.ready.weddingplanner.service;

import com.ready.weddingplanner.domain.*;
import com.ready.weddingplanner.repository.BudgetItemRepository;
import com.ready.weddingplanner.repository.BudgetPlanRepository;
import com.ready.weddingplanner.repository.PartnerLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetPlanRepository budgetPlanRepository;
    private final BudgetItemRepository budgetItemRepository;
    private final PartnerLinkRepository partnerLinkRepository;
    private final UserService userService;

    @Transactional
    public BudgetPlan getBudget() {
        User currentUser = userService.getMe();
        return budgetPlanRepository.findByUser(currentUser)
                .orElseGet(() -> createBudgetPlan(currentUser));
    }

    private BudgetPlan createBudgetPlan(User user) {
        PartnerLink partnerLink = partnerLinkRepository.findByUserOrPartner(user, user)
                .orElseThrow(() -> new IllegalStateException("Budget can only be created after partner link is established."));

        BudgetPlan newPlan = new BudgetPlan();
        newPlan.setUser(user);
        newPlan.setTargetAmount(partnerLink.getBudgetAmount());
        return budgetPlanRepository.save(newPlan);
    }

    @Transactional(readOnly = true)
    public List<BudgetItem> getBudgetItems() {
        BudgetPlan budgetPlan = getBudget();
        return budgetItemRepository.findByBudgetPlan(budgetPlan);
    }

    @Transactional(readOnly = true)
    public BudgetPlan getBudgetPlanForUser(User user) {
        return budgetPlanRepository.findByUser(user).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<BudgetItem> getBudgetItemsForPlan(BudgetPlan plan) {
        return budgetItemRepository.findByBudgetPlan(plan);
    }
}
