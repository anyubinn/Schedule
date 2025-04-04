package com.example.schedule.controller;

import com.example.schedule.dto.request.UpdateUserRequestDto;
import com.example.schedule.dto.request.UserRequestDto;
import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.entity.User;
import com.example.schedule.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저의 CRUD를 담당하는 컨트롤러
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입한다.
     *
     * @param dto 등록할 유저 정보를 담고 있는 DTO
     * @return 생성된 유저 정보와 HTTP 상태(CREATED)
     */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserRequestDto dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(dto));
    }

    /**
     * 모든 유저를 조회한다.
     *
     * @return 조회된 유저 리스트 정보와 HTTP 상태(200 OK)
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {

        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * 특정 유저를 조회한다.
     *
     * @param id 조회할 유저의 id
     * @return 조회된 유저 정보와 HTTP 상태(200 OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * 특정 유저를 수정한다.
     *
     * @param id 수정할 유저의 id
     * @param dto 수정할 유저의 정보를 담고 있는 DTO
     * @param loginUser 현재 로그인된 유저 정보
     * @return 수정된 유저 정보와 HTTP 상태(200 OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDto dto,
                                                      @ModelAttribute("loginUser") User loginUser) {

        return ResponseEntity.ok(userService.updateUser(id, dto, loginUser));
    }

    /**
     * 특정 유저를 삭제한다.
     *
     * @param id 삭제할 유저의 id
     * @param loginUser 현재 로그인된 유저 정보
     * @return 삭제 성공 시 HTTP 상태(200 OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @ModelAttribute("loginUser") User loginUser) {

        userService.delete(id, loginUser);

        return ResponseEntity.ok().build();
    }
}
