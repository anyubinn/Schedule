package com.example.schedule.repository;

import com.example.schedule.entity.Comment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndScheduleId(Long id, Long scheduleId);

    default Comment findByIdAndScheduleIdOrElseThrow(Long id, Long scheduleId) {

        return findByIdAndScheduleId(id, scheduleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."));
    }
}
