package com.example.schedule.dto.response;

import com.example.schedule.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 응답에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private final Long id;

    private final String userName;

    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static CommentResponseDto toDto(Comment comment) {

        return new CommentResponseDto(comment.getId(), comment.getUser().getUserName(), comment.getContents(),
                comment.getCreatedAt(), comment.getUpdatedAt());
    }
}
