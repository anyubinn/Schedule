package com.example.schedule.service;

import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    public ScheduleResponseDto save(ScheduleRequestDto dto, HttpServletRequest request) {

        User findUser = isLoggedIn(request);

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
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto dto, HttpServletRequest request) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        User findUser = isLoggedIn(request);

        if (findSchedule.getUser().getId() != findUser.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 일정 작성자만 수정이 가능합니다.");
        }

        findSchedule.updateSchedule(dto.getTitle(), dto.getContents());

        return ScheduleResponseDto.toDto(findSchedule);
    }

    public void delete(Long id, HttpServletRequest request) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        User findUser = isLoggedIn(request);

        if (findSchedule.getUser().getId() != findUser.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 일정 작성자만 삭제가 가능합니다.");
        }

        scheduleRepository.delete(findSchedule);
    }

    private User isLoggedIn(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("sessionKey");

        return userRepository.findByEmailOrElseThrow(email);
    }
}
