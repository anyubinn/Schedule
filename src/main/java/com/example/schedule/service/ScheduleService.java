package com.example.schedule.service;

import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto save(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule(dto.getUserName(), dto.getTitle(), dto.getContents());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.toDto(savedSchedule);
    }

    public List<ScheduleResponseDto> findAll(String userName) {

        if (userName == null) {

            return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
        }

        return scheduleRepository.findAllByUserName(userName).stream().map(ScheduleResponseDto::toDto).toList();
    }
}
