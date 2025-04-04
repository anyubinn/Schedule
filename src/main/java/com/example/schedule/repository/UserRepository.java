package com.example.schedule.repository;

import com.example.schedule.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 유저 관련 DB 작업을 처리하는 리포지토리
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일을 기준으로 특정 유저를 조회한다.
     *
     * @param email 조회할 유저 이메일
     * @return 조회된 유저 정보
     */
    Optional<User> findByEmail(String email);

    /**
     * 특정 유저를 조회한다.
     *
     * @param id 조회할 유저 id
     * @return 조회된 유저 정보, 없으면 404 Not Found 예외 발생
     */
    default User findByIdOrElseThrow(Long id) {

        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));
    }

    /**
     * 이메일을 기준으로 특정 유저를 조회한다.
     *
     * @param email 조회할 유저 이메일
     * @return 조회된 유저 정보, 없으면 404 Not Found 예외 발생
     */
    default User findByEmailOrElseThrow(String email) {

        return findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));
    }
}
