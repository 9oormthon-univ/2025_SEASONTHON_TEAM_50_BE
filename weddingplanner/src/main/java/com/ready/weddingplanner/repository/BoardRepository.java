package com.ready.weddingplanner.repository;

import com.ready.weddingplanner.domain.Board;
import com.ready.weddingplanner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    long countByOwner(User owner);
}
