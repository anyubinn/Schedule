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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto save(Long scheduleId, CommentRequestDto dto, User loginUser) {

        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(loginUser, findSchedule, dto.getContents());
        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.toDto(saveComment);
    }

    public List<CommentResponseDto> findAll(Long scheduleId, String userName) {

        scheduleRepository.findByIdOrElseThrow(scheduleId);

        if (userName == null) {

            return commentRepository.findAllByScheduleId(scheduleId).stream().map(CommentResponseDto::toDto).toList();
        }

        return commentRepository.findAllByScheduleIdAndUser_UserName(scheduleId, userName).stream().map(CommentResponseDto::toDto).toList();
    }

    public CommentResponseDto findById(Long scheduleId, Long commentId) {

        scheduleRepository.findByIdOrElseThrow(scheduleId);
        Comment findComment = commentRepository.findByIdAndScheduleIdOrElseThrow(commentId, scheduleId);

        return CommentResponseDto.toDto(findComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, UpdateCommentRequestDto dto,
                                            User loginUser) {

        Comment findComment = validateUserAuth(loginUser, scheduleId, commentId);

        findComment.updateComment(dto.getContents());

        return CommentResponseDto.toDto(findComment);
    }

    public void delete(Long scheduleId, Long commentId, User loginUser) {

        Comment findComment = validateUserAuth(loginUser, scheduleId, commentId);

        commentRepository.delete(findComment);
    }

    private Comment validateUserAuth(User user, Long scheduleId, Long commentId) {

        scheduleRepository.findByIdOrElseThrow(scheduleId);
        Comment findComment = commentRepository.findByIdAndScheduleIdOrElseThrow(commentId, scheduleId);

        if (findComment.getUser().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 댓글 작성자만 수정/삭제가 가능합니다.");
        }

        return findComment;
    }
}
