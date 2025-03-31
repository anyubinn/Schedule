package com.example.schedule.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateScheduleRequestDto {

    private final String title;

    private final String contents;

    private final String password;
}
