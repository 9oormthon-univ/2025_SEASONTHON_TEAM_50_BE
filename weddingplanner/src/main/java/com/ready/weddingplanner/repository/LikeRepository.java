package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.BoardItem;
import com.ready.weddingplanner.domain.Like;
import com.ready.weddingplanner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndBoardItem(User user, BoardItem boardItem);

    long countByBoardItem(BoardItem boardItem);

    long countByBoardItem_Board_Id(Long boardId);

    long countByBoardItem_Board_Owner(User owner);
}
