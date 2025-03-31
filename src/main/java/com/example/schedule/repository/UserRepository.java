package com.example.schedule.repository;

import com.example.schedule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    default User findByIdOrElseThrow(Long id) {

        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));
    }
}
