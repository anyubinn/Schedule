package com.example.schedule.dto.response;

import com.example.schedule.entity.Schedule;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일정 생성, 수정 응답에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private final Long id;

    private final String userName;

    private final String title;

    private final String contents;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static ScheduleResponseDto toDto(Schedule schedule) {

        return new ScheduleResponseDto(schedule.getId(), schedule.getUser().getUserName(), schedule.getTitle(),
                schedule.getContents(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }
}
