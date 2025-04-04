package com.example.schedule.repository;

import com.example.schedule.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 댓글 관련 DB 작업을 처리하는 리포지토리
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 댓글을 조회한다.
     *
     * @param id 조회할 댓글 id
     * @param scheduleId 댓글이 속한 일정 id
     * @return 조회된 댓글 정보
     */
    Optional<Comment> findByIdAndScheduleId(Long id, Long scheduleId);

    /**
     * 특정 일정 내 모든 댓글을 조회한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @return 조회된 댓글 리스트
     */
    List<Comment> findAllByScheduleId(Long scheduleId);

    /**
     * 특정 일정 내 특정 유저가 작성한 모든 댓글을 조회한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @param userName 조회할 유저명
     * @return 조회된 댓글 리스트
     */
    List<Comment> findAllByScheduleIdAndUser_UserName(Long scheduleId, String userName);

    /**
     * 특정 일정에 속한 댓글 수를 조회한다.
     *
     * @param scheduleId 댓글이 속한 일정 id
     * @return 해당 일정의 댓글 수
     */
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.schedule.id = :scheduleId")
    int countCommentByScheduleId(@Param("scheduleId") Long scheduleId);

    /**
     * 특정 일정에 속한 특정 댓글을 조회한다.
     *
     * @param id 조회할 댓글 id
     * @param scheduleId 댓글이 속한 일정 id
     * @return 조회된 댓글, 없으면 404 Not Found 예외 발생
     */
    default Comment findByIdAndScheduleIdOrElseThrow(Long id, Long scheduleId) {

        return findByIdAndScheduleId(id, scheduleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글이 존재하지 않습니다."));
    }
}
