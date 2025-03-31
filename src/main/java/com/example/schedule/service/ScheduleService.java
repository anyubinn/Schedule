package com.example.schedule.service;

import com.example.schedule.dto.request.DeleteRequestDto;
import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto save(ScheduleRequestDto dto) {

        User findUser = userRepository.findByUserName(dto.getUserName());

        if (!findUser.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        Schedule schedule = new Schedule(findUser, dto.getTitle(), dto.getContents());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.toDto(savedSchedule);
    }

    public List<ScheduleResponseDto> findAll(String userName) {

        if (userName == null) {

            return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
        }

        return scheduleRepository.findAllByUser_UserName(userName).stream().map(ScheduleResponseDto::toDto).toList();
    }

    public ScheduleResponseDto findById(Long id) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        return ScheduleResponseDto.toDto(findSchedule);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto dto) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        if (!findSchedule.getUser().getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findSchedule.updateSchedule(dto.getTitle(), dto.getContents());

        return ScheduleResponseDto.toDto(findSchedule);
    }

    public void delete(Long id, DeleteRequestDto dto) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        if (!findSchedule.getUser().getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.delete(findSchedule);
    }
}
