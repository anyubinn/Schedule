package com.example.schedule.dto.response;

import com.example.schedule.entity.Schedule;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일정 조회 응답에 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class ReadScheduleResponseDto {

    private final Long id;

    private final String userName;

    private final String title;

    private final String contents;

    private final int commentCount;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public static ReadScheduleResponseDto toDto(Schedule schedule, int commentCount) {

        return new ReadScheduleResponseDto(schedule.getId(), schedule.getUser().getUserName(), schedule.getTitle(),
                schedule.getContents(), commentCount, schedule.getCreatedAt(), schedule.getUpdatedAt());
    }
}
