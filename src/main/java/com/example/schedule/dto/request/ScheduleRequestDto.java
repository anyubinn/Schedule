package com.example.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {

    @Size(max = 10, message = "할일 제목은 10자까지 입력이 가능합니다.")
    @NotBlank(message = "할일 제목은 필수값입니다.")
    private final String title;

    @NotBlank(message = "할일 내용은 필수값입니다.")
    private final String contents;
}
