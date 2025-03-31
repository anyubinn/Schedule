package com.example.schedule.controller;

import com.example.schedule.dto.request.UserRequestDto;
import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> save(@RequestBody UserRequestDto dto) {

        return new ResponseEntity<>(userService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {

        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.findById(id));
    }
}
