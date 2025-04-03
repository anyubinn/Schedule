package com.example.schedule.service;

import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.schedule.dto.response.ReadScheduleResponseDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    public ScheduleResponseDto save(ScheduleRequestDto dto, HttpServletRequest request) {

        User findUser = validateLoggedIn(request);

        Schedule schedule = new Schedule(findUser, dto.getTitle(), dto.getContents());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.toDto(savedSchedule);
    }

    public Page<ReadScheduleResponseDto> findAll(String userName, Pageable pageable) {

        Page<Schedule> schedules;

        if (userName == null) {
            schedules = scheduleRepository.findAll(pageable);
        } else {
            schedules = scheduleRepository.findAllByUser_UserName(userName, pageable);
        }

        return schedules.map(schedule -> {
            int commentCount = commentRepository.countCommentByScheduleId(schedule.getId());
            return ReadScheduleResponseDto.toDto(schedule, commentCount);
        });
    }

    public ReadScheduleResponseDto findById(Long id) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        int commentCount = commentRepository.countCommentByScheduleId(findSchedule.getId());

        return ReadScheduleResponseDto.toDto(findSchedule, commentCount);
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto dto, HttpServletRequest request) {

        User findUser = validateLoggedIn(request);
        Schedule findSchedule = validateUserAuth(findUser, id);

        findSchedule.updateSchedule(dto.getTitle(), dto.getContents());

        return ScheduleResponseDto.toDto(findSchedule);
    }

    public void delete(Long id, HttpServletRequest request) {

        User findUser = validateLoggedIn(request);
        Schedule findSchedule = validateUserAuth(findUser, id);

        scheduleRepository.delete(findSchedule);
    }

    private User validateLoggedIn(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("sessionKey");

        return userRepository.findByEmailOrElseThrow(email);
    }

    private Schedule validateUserAuth(User user, Long id) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        if (findSchedule.getUser().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 일정 작성자만 수정/삭제가 가능합니다.");
        }

        return findSchedule;
    }
}
