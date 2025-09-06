package com.ready.weddingplanner.dto;

import com.ready.weddingplanner.domain.Board;
import com.ready.weddingplanner.domain.BoardItem;
import com.ready.weddingplanner.domain.Comment;
import com.ready.weddingplanner.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDto {

    @Data
    @NoArgsConstructor
    public static class BoardRequest {
        private String title;
        private Board.Visibility visibility;
    }

    @Data
    @NoArgsConstructor
    public static class BoardResponse {
        private Long id;
        private String title;
        private Board.Visibility visibility;
        private Long ownerId;
        private int itemCount;

        public BoardResponse(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.visibility = board.getVisibility();
            this.ownerId = board.getOwner().getId();
        }
    }

    @Data
    @NoArgsConstructor
    public static class BoardDetailsResponse extends BoardResponse {
        private List<BoardItemDto> items;
        private long likeCount;
        private long commentCount;

        public BoardDetailsResponse(Board board, List<BoardItem> items, long likeCount, long commentCount) {
            super(board);
            this.items = items.stream().map(BoardItemDto::new).collect(Collectors.toList());
            this.likeCount = likeCount;
            this.commentCount = commentCount;
        }
    }

    @Data
    @NoArgsConstructor
    public static class BoardItemRequest {
        private String imageUrl;
        private String sourceUrl;
        private String category;
        private List<String> tags;
        private String memo;
    }

    @Data
    @NoArgsConstructor
    public static class BoardItemDto {
        private Long id;
        private String imageUrl;
        private String category;
        private List<String> tags;
        private String memo;

        public BoardItemDto(BoardItem item) {
            this.id = item.getId();
            this.imageUrl = item.getImageUrl();
            this.category = item.getCategory();
            if (item.getTagsCsv() != null && !item.getTagsCsv().isEmpty()) {
                this.tags = Arrays.asList(item.getTagsCsv().split(","));
            }
            this.memo = item.getMemo();
        }
    }

    @Data
    @NoArgsConstructor
    public static class LikeRequest {
        private boolean liked;
    }

    @Data
    @NoArgsConstructor
    public static class LikeResponse {
        private boolean liked;
        private long count;
    }

    @Data
    @NoArgsConstructor
    public static class CommentRequest {
        private String content;
    }

    @Data
    @NoArgsConstructor
    public static class CommentResponse {
        private Long id;
        private String content;
        private String authorName;
        private LocalDateTime createdAt;

        public CommentResponse(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContent();
            this.authorName = comment.getUser().getName();
            this.createdAt = comment.getCreatedAt();
        }
    }
}
