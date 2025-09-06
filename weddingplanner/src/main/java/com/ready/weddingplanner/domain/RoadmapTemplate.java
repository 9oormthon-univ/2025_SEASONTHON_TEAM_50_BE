package com.ready.weddingplanner.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "roadmap_templates")
@Getter
@Setter
@NoArgsConstructor
public class RoadmapTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "due_relative_month", nullable = false)
    private int dueRelativeMonth;

    @Column(nullable = false, length = 30)
    private String category;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = true;
}
