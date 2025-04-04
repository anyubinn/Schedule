package com.example.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일정 수정 요청에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class UpdateScheduleRequestDto {

    private final String title;

    private final String contents;
}
