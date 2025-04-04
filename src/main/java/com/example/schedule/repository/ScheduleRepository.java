package com.example.schedule.repository;

import com.example.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 일정 관련 DB 작업을 처리하는 리포지토리
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 특정 유저가 작성한 모든 일정을 페이징 처리하여 조회한다.
     *
     * @param userName 조회할 유저명
     * @param pageable 페이징 정보
     * @return 페이징된 일정 리스트
     */
    Page<Schedule> findAllByUser_UserName(String userName, Pageable pageable);

    /**
     * 특정 일정을 조회한다.
     *
     * @param id 조회할 일정 id
     * @return 조회된 댓글, 없으면 404 Not Found 예외 발생
     */
    default Schedule findByIdOrElseThrow(Long id) {

        return findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 일정이 존재하지 않습니다."));
    }
}
