package com.ready.weddingplanner.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "budget_items")
@Getter
@Setter
@NoArgsConstructor
public class BudgetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_plan_id", nullable = false)
    private BudgetPlan budgetPlan;

    @Column(nullable = false, length = 30)
    private String category;

    @Column(nullable = false)
    private Long planned = 0L;

    @Column(nullable = false)
    private Long actual = 0L;
}
