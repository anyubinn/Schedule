package com.example.schedule.service;

import com.example.schedule.dto.request.CommentRequestDto;
import com.example.schedule.dto.request.UpdateCommentRequestDto;
import com.example.schedule.dto.response.CommentResponseDto;
import com.example.schedule.entity.Comment;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentResponseDto save(Long scheduleId, CommentRequestDto dto, HttpServletRequest request) {

        User findUser = validateLoggedIn(request);
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(scheduleId);

        Comment comment = new Comment(findUser, findSchedule, dto.getContents());
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
                                            HttpServletRequest request) {

        User findUser = validateLoggedIn(request);
        Comment findComment = validateUserAuth(findUser, scheduleId, commentId);

        findComment.updateComment(dto.getContents());

        return CommentResponseDto.toDto(findComment);
    }

    public void delete(Long scheduleId, Long commentId, HttpServletRequest request) {

        User findUser = validateLoggedIn(request);
        Comment findComment = validateUserAuth(findUser, scheduleId, commentId);

        commentRepository.delete(findComment);
    }

    private User validateLoggedIn(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        String email = (String) session.getAttribute("sessionKey");

        return userRepository.findByEmailOrElseThrow(email);
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
