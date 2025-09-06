package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.BoardItem;
import com.ready.weddingplanner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardItemRepository extends JpaRepository<BoardItem, Long> {
    List<BoardItem> findByBoardId(Long boardId);
    long countByBoard_Owner(User owner);
}
