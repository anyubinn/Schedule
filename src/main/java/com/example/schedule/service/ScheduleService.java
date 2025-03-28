package com.example.schedule.service;

import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto save(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto.getUserName(), dto.getTitle(), dto.getContents());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getUserName(), savedSchedule.getTitle(),
                savedSchedule.getContents(), savedSchedule.getCreatedAt(), savedSchedule.getUpdatedAt());
    }
}
