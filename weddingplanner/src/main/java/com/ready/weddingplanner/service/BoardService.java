package com.ready.weddingplanner.service;

import com.ready.weddingplanner.domain.*;
import com.ready.weddingplanner.dto.BoardDto;
import com.ready.weddingplanner.repository.BoardItemRepository;
import com.ready.weddingplanner.repository.BoardRepository;
import com.ready.weddingplanner.repository.CommentRepository;
import com.ready.weddingplanner.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardItemRepository boardItemRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional
    public Board createBoard(BoardDto.BoardRequest request) {
        User currentUser = userService.getMe();
        Board board = new Board();
        board.setTitle(request.getTitle());
        board.setVisibility(request.getVisibility());
        board.setOwner(currentUser);
        return boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
    }

    @Transactional(readOnly = true)
    public List<BoardItem> getBoardItems(Long boardId) {
        return boardItemRepository.findByBoardId(boardId);
    }

    @Transactional
    public BoardItem createBoardItem(Long boardId, BoardDto.BoardItemRequest request) {
        User currentUser = userService.getMe();
        Board board = getBoard(boardId);
        if (!board.getOwner().equals(currentUser)) {
            throw new IllegalStateException("You are not the owner of this board");
        }

        BoardItem item = new BoardItem();
        item.setBoard(board);
        item.setImageUrl(request.getImageUrl());
        item.setSourceUrl(request.getSourceUrl());
        item.setCategory(request.getCategory());
        if (request.getTags() != null) {
            item.setTagsCsv(String.join(",", request.getTags()));
        }
        item.setMemo(request.getMemo());

        return boardItemRepository.save(item);
    }

    @Transactional
    public long likeBoardItem(Long itemId, boolean liked) {
        User currentUser = userService.getMe();
        BoardItem item = boardItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        if (liked) {
            if (likeRepository.findByUserAndBoardItem(currentUser, item).isEmpty()) {
                Like like = new Like();
                like.setUser(currentUser);
                like.setBoardItem(item);
                likeRepository.save(like);
            }
        } else {
            likeRepository.findByUserAndBoardItem(currentUser, item)
                    .ifPresent(likeRepository::delete);
        }
        return likeRepository.countByBoardItem(item);
    }

    @Transactional(readOnly = true)
    public long getLikeCount(Long boardId) {
        return likeRepository.countByBoardItem_Board_Id(boardId);
    }

    @Transactional(readOnly = true)
    public long getCommentCount(Long boardId) {
        return commentRepository.countByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getComments(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    @Transactional
    public Comment createComment(Long boardId, BoardDto.CommentRequest request) {
        User currentUser = userService.getMe();
        Board board = getBoard(boardId);

        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setUser(currentUser);
        comment.setContent(request.getContent());

        return commentRepository.save(comment);
    }
}
