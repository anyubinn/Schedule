package com.example.schedule.service;

import com.example.schedule.dto.request.ScheduleRequestDto;
import com.example.schedule.dto.request.UpdateScheduleRequestDto;
import com.example.schedule.dto.response.ReadScheduleResponseDto;
import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * 일정 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    /**
     * 새로운 일정을 저장한다.
     *
     * @param dto 추가할 일정 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 저장된 일정 정보에 대한 응답 DTO
     */
    public ScheduleResponseDto save(ScheduleRequestDto dto, User loginUser) {

        Schedule schedule = new Schedule(loginUser, dto.getTitle(), dto.getContents());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleResponseDto.toDto(savedSchedule);
    }

    /**
     * 모든 일정을 조회한다.
     * @param userName 검색할 유저명
     * @param pageable 페이징 정보
     * @return 조회된 일정 정보에 대한 응답 DTO
     */
    public Page<ReadScheduleResponseDto> findAll(String userName, Pageable pageable) {

        Page<Schedule> schedules;

        // 검색할 유저명이 없는 경우
        if (userName == null) {
            schedules = scheduleRepository.findAll(pageable);
        } else {
            schedules = scheduleRepository.findAllByUser_UserName(userName, pageable);
        }

        return schedules.map(schedule -> {
            // 댓글수 계산하여 응답 결과로 반환
            int commentCount = commentRepository.countCommentByScheduleId(schedule.getId());
            return ReadScheduleResponseDto.toDto(schedule, commentCount);
        });
    }

    /**
     * 특정 일정을 조회한다.
     * @param id 조회할 일정의 id
     * @return 조회된 일정 정보에 대한 응답 DTO
     */
    public ReadScheduleResponseDto findById(Long id) {

        // 일정이 존재하는지 검색
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);
        // 댓글수 계산하여 응답 결과로 반환
        int commentCount = commentRepository.countCommentByScheduleId(findSchedule.getId());

        return ReadScheduleResponseDto.toDto(findSchedule, commentCount);
    }

    /**
     * 특정 일정을 수정한다.
     * @param id 수정할 일정의 id
     * @param dto 수정할 일정 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 수정된 일정 정보에 대한 응답 DTO
     */
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, UpdateScheduleRequestDto dto, User loginUser) {

        // 일정을 작성한 유저가 맞는지 확인
        Schedule findSchedule = validateUserAuth(loginUser, id);
        findSchedule.updateSchedule(dto.getTitle(), dto.getContents());

        return ScheduleResponseDto.toDto(findSchedule);
    }

    /**
     * 특정 일정을 삭제한다.
     *
     * @param id 삭제할 일정의 id
     * @param loginUser 현재 로그인된 유저 정보
     */
    public void delete(Long id, User loginUser) {

        // 일정을 작성한 유저가 맞는지 확인
        Schedule findSchedule = validateUserAuth(loginUser, id);

        scheduleRepository.delete(findSchedule);
    }

    /**
     * 일정 수정/삭제 시 유저 권한을 검증한다.
     *
     * @param user 현재 로그인한 유저 정보
     * @param id 일정 id
     * @return 일정 정보, 권한 인증 실패시 403 Forbidden 예외 발생
     */
    private Schedule validateUserAuth(User user, Long id) {

        // 일정이 존재하는지 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 현재 로그인된 유저와 일정 작성자가 다른 경우
        if (findSchedule.getUser().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 일정 작성자만 수정/삭제가 가능합니다.");
        }

        return findSchedule;
    }
}
