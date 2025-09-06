package com.ready.weddingplanner.controller;

import com.ready.weddingplanner.domain.Board;
import com.ready.weddingplanner.domain.BoardItem;
import com.ready.weddingplanner.domain.Comment;
import com.ready.weddingplanner.dto.BoardDto;
import com.ready.weddingplanner.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<Page<BoardDto.BoardResponse>> getBoards(Pageable pageable) {
        Page<Board> boards = boardService.getBoards(pageable);
        Page<BoardDto.BoardResponse> response = boards.map(BoardDto.BoardResponse::new);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BoardDto.BoardResponse> createBoard(@RequestBody BoardDto.BoardRequest request) {
        Board newBoard = boardService.createBoard(request);
        return ResponseEntity.ok(new BoardDto.BoardResponse(newBoard));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDto.BoardDetailsResponse> getBoardDetails(@PathVariable Long id) {
        Board board = boardService.getBoard(id);
        List<BoardItem> items = boardService.getBoardItems(id);
        long likeCount = boardService.getLikeCount(id);
        long commentCount = boardService.getCommentCount(id);
        return ResponseEntity.ok(new BoardDto.BoardDetailsResponse(board, items, likeCount, commentCount));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<BoardDto.BoardItemDto> createBoardItem(@PathVariable Long id, @RequestBody BoardDto.BoardItemRequest request) {
        BoardItem newItem = boardService.createBoardItem(id, request);
        return ResponseEntity.ok(new BoardDto.BoardItemDto(newItem));
    }

    @PostMapping("/items/{itemId}/like")
    public ResponseEntity<BoardDto.LikeResponse> likeBoardItem(@PathVariable Long itemId, @RequestBody BoardDto.LikeRequest request) {
        long count = boardService.likeBoardItem(itemId, request.isLiked());
        BoardDto.LikeResponse response = new BoardDto.LikeResponse();
        response.setLiked(request.isLiked());
        response.setCount(count);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<BoardDto.CommentResponse> createComment(@PathVariable Long id, @RequestBody BoardDto.CommentRequest request) {
        Comment newComment = boardService.createComment(id, request);
        return ResponseEntity.ok(new BoardDto.CommentResponse(newComment));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<BoardDto.CommentResponse>> getComments(@PathVariable Long id) {
        List<Comment> comments = boardService.getComments(id);
        List<BoardDto.CommentResponse> response = comments.stream()
                .map(BoardDto.CommentResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
