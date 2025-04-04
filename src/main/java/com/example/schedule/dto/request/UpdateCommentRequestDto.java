package com.example.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 수정 요청에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class UpdateCommentRequestDto {

    private final String contents;
}
