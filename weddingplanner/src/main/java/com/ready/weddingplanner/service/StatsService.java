package com.ready.weddingplanner.service;

import com.ready.weddingplanner.domain.*;
import com.ready.weddingplanner.dto.StatsDto;
import com.ready.weddingplanner.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final UserService userService;
    private final BoardRepository boardRepository;
    private final BoardItemRepository boardItemRepository;
    private final LikeRepository likeRepository;
    private final PartnerLinkRepository partnerLinkRepository;
    private final BudgetPlanRepository budgetPlanRepository;
    private final BudgetItemRepository budgetItemRepository;
    private final UserTaskRepository userTaskRepository;

    @Transactional(readOnly = true)
    public StatsDto getMyStats() {
        User currentUser = userService.getMe();
        StatsDto statsDto = new StatsDto();

        statsDto.setRomance(getRomanceStats(currentUser));
        statsDto.setTime(getTimeStats(currentUser));
        statsDto.setBudget(getBudgetStats(currentUser));
        statsDto.setTasks(getTaskStats(currentUser));

        return statsDto;
    }

    private StatsDto.RomanceStats getRomanceStats(User user) {
        StatsDto.RomanceStats romanceStats = new StatsDto.RomanceStats();
        romanceStats.setBoardCount(boardRepository.countByOwner(user));
        romanceStats.setItemCount(boardItemRepository.countByBoard_Owner(user));
        romanceStats.setLikes(likeRepository.countByBoardItem_Board_Owner(user));
        return romanceStats;
    }

    private StatsDto.TimeStats getTimeStats(User user) {
        StatsDto.TimeStats timeStats = new StatsDto.TimeStats();
        partnerLinkRepository.findByUserOrPartner(user, user).ifPresent(link -> {
            if (link.getWeddingDate() != null) {
                timeStats.setWeddingDate(link.getWeddingDate());
                timeStats.setDaysLeft(ChronoUnit.DAYS.between(LocalDate.now(), link.getWeddingDate()));
            }
        });
        return timeStats;
    }

    private StatsDto.BudgetStats getBudgetStats(User user) {
        StatsDto.BudgetStats budgetStats = new StatsDto.BudgetStats();
        budgetPlanRepository.findByUser(user).ifPresent(plan -> {
            List<BudgetItem> items = budgetItemRepository.findByBudgetPlan(plan);
            long planned = items.stream().mapToLong(BudgetItem::getPlanned).sum();
            long actual = items.stream().mapToLong(BudgetItem::getActual).sum();
            budgetStats.setTarget(plan.getTargetAmount());
            budgetStats.setPlanned(planned);
            budgetStats.setActual(actual);
            if (planned > 0) {
                budgetStats.setProgress((double) actual / planned);
            }
        });
        return budgetStats;
    }

    private StatsDto.TaskStats getTaskStats(User user) {
        StatsDto.TaskStats taskStats = new StatsDto.TaskStats();
        long todo = userTaskRepository.countByUserAndStatus(user, UserTask.TaskStatus.TODO);
        long doing = userTaskRepository.countByUserAndStatus(user, UserTask.TaskStatus.DOING);
        long done = userTaskRepository.countByUserAndStatus(user, UserTask.TaskStatus.DONE);
        long total = todo + doing + done;

        taskStats.setTodo((int) todo);
        taskStats.setDoing((int) doing);
        taskStats.setDone((int) done);
        if (total > 0) {
            taskStats.setCompletionRate((double) done / total);
        }
        return taskStats;
    }
}
