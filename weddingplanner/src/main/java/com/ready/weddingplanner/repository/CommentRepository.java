package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardId(Long boardId);
    long countByBoardId(Long boardId);
}
