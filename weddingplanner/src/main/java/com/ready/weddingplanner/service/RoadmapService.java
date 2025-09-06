package com.ready.weddingplanner.service;

import com.ready.weddingplanner.domain.*;
import com.ready.weddingplanner.repository.PartnerLinkRepository;
import com.ready.weddingplanner.repository.RoadmapTemplateRepository;
import com.ready.weddingplanner.repository.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoadmapService {

    private final RoadmapTemplateRepository roadmapTemplateRepository;
    private final UserTaskRepository userTaskRepository;
    private final PartnerLinkRepository partnerLinkRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<RoadmapTemplate> getRoadmapTemplates() {
        return roadmapTemplateRepository.findAll();
    }

    @Transactional
    public int instantiateRoadmap() {
        User currentUser = userService.getMe();
        PartnerLink partnerLink = partnerLinkRepository.findByUserOrPartner(currentUser, currentUser)
                .orElseThrow(() -> new IllegalStateException("Wedding date not set"));

        LocalDate weddingDate = partnerLink.getWeddingDate();
        if (weddingDate == null) {
            throw new IllegalStateException("Wedding date not set");
        }

        List<RoadmapTemplate> templates = roadmapTemplateRepository.findAll();
        List<UserTask> tasks = templates.stream().map(template -> {
            UserTask task = new UserTask();
            task.setUser(currentUser);
            task.setTitle(template.getTitle());
            task.setCategory(template.getCategory());
            task.setStatus(UserTask.TaskStatus.TODO);
            task.setDueDate(weddingDate.plusMonths(template.getDueRelativeMonth()));
            return task;
        }).collect(Collectors.toList());

        return userTaskRepository.saveAll(tasks).size();
    }

    @Transactional(readOnly = true)
    public List<UserTask> getMyTasks() {
        User currentUser = userService.getMe();
        return userTaskRepository.findByUser(currentUser);
    }
}
