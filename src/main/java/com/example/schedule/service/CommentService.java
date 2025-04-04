package com.example.schedule.service;

import com.example.schedule.dto.request.CommentRequestDto;
import com.example.schedule.dto.request.UpdateCommentRequestDto;
import com.example.schedule.dto.response.CommentResponseDto;
import com.example.schedule.entity.Comment;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * 댓글 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    /**
     * 특정 일정에 대한 새로운 댓글을 저장한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param dto 추가할 댓글 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 저장된 댓글 정보에 대한 응답 DTO
     */
    public CommentResponseDto save(Long scheduleId, CommentRequestDto dto, User loginUser) {

        // 일정이 존재하는지 조회
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        // 댓글 저장
        Comment comment = new Comment(loginUser, findSchedule, dto.getContents());
        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.toDto(saveComment);
    }

    /**
     * 특정 일정에 대한 모든 댓글을 조회한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param userName 검색할 유저명
     * @return 조회된 댓글 리스트 정보에 대한 응답 DTO
     */
    public List<CommentResponseDto> findAll(Long scheduleId, String userName) {

        // 일정이 존재하는지 조회
        scheduleRepository.findByIdOrElseThrow(scheduleId);

        // 검색할 유저명이 없는 경우
        if (userName == null) {

            return commentRepository.findAllByScheduleId(scheduleId).stream().map(CommentResponseDto::toDto).toList();
        }

        return commentRepository.findAllByScheduleIdAndUser_UserName(scheduleId, userName).stream().map(CommentResponseDto::toDto).toList();
    }

    /**
     * 특정 일정에 대한 특정 댓글을 조회한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param commentId 조회할 댓글 id
     * @return 조회된 댓글 정보에 대한 응답 DTO
     */
    public CommentResponseDto findById(Long scheduleId, Long commentId) {

        // 일정이 존재하는지 조회
        scheduleRepository.findByIdOrElseThrow(scheduleId);
        Comment findComment = commentRepository.findByIdAndScheduleIdOrElseThrow(commentId, scheduleId);

        return CommentResponseDto.toDto(findComment);
    }

    /**
     * 특정 일정에 대한 특정 댓글을 수정한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param commentId 수정할 댓글 id
     * @param dto 수정할 댓글 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 수정된 댓글 정보에 대한 응답 DTO
     */
    @Transactional
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, UpdateCommentRequestDto dto,
                                            User loginUser) {

        // 댓글을 작성한 유저가 맞는지 확인
        Comment findComment = validateUserAuth(loginUser, scheduleId, commentId);
        findComment.updateComment(dto.getContents());

        return CommentResponseDto.toDto(findComment);
    }

    /**
     * 특정 일정에 대한 특정 댓글을 삭제한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param commentId 삭제할 댓글 id
     * @param loginUser 현재 로그인된 유저 정보
     */
    public void delete(Long scheduleId, Long commentId, User loginUser) {

        // 댓글을 작성한 유저가 맞는지 확인
        Comment findComment = validateUserAuth(loginUser, scheduleId, commentId);

        commentRepository.delete(findComment);
    }

    /**
     * 댓글 수정/삭제 시 유저 권한을 검증한다.
     *
     * @param user 현재 로그인한 유저 정보
     * @param scheduleId 댓글이 속한 일정 id
     * @param commentId 댓글 id
     * @return 댓글 정보, 권한 인증 실패시 403 Forbidden 예외 발생
     */
    private Comment validateUserAuth(User user, Long scheduleId, Long commentId) {

        // 일정이 존재하는지 조회
        scheduleRepository.findByIdOrElseThrow(scheduleId);
        Comment findComment = commentRepository.findByIdAndScheduleIdOrElseThrow(commentId, scheduleId);

        // 현재 로그인된 유저와 댓글 작성자가 다른 경우
        if (findComment.getUser().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 댓글 작성자만 수정/삭제가 가능합니다.");
        }

        return findComment;
    }
}
