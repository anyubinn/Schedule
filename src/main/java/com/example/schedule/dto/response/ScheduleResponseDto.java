package com.example.schedule.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private final Long id;

    private final String userName;

    private final String title;

    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;
}
