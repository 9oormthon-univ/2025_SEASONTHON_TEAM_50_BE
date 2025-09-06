package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.User;
import com.ready.weddingplanner.domain.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    List<UserTask> findByUser(User user);
    long countByUserAndStatus(User user, UserTask.TaskStatus status);
}
