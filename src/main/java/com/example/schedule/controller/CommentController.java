package com.example.schedule.controller;

import com.example.schedule.dto.request.CommentRequestDto;
import com.example.schedule.dto.response.CommentResponseDto;
import com.example.schedule.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll(@PathVariable Long scheduleId,
                                                            @RequestParam(required = false) String userName) {

        return ResponseEntity.ok(commentService.findAll(scheduleId, userName));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long scheduleId, @PathVariable Long commentId) {

        return ResponseEntity.ok(commentService.findById(scheduleId, commentId));
    }
}
