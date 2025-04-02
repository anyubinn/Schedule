package com.example.schedule.controller;

import com.example.schedule.dto.request.CommentRequestDto;
import com.example.schedule.dto.response.CommentResponseDto;
import com.example.schedule.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> save(@PathVariable Long scheduleId, @RequestBody CommentRequestDto dto,
                                                   HttpServletRequest request) {

        return new ResponseEntity<>(commentService.save(scheduleId, dto, request), HttpStatus.CREATED);
    }
}
