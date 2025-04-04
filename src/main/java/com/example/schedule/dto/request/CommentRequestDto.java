package com.example.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 추가 요청에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 필수값입니다.")
    private final String contents;
}
