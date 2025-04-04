package com.example.schedule.handler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * 애플리케이션 전역에서 발생하는 예외를 처리하는 핸들러 클래스
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Valid 검증에서 실패할 경우 발생하는 예외를 처리한다.
     *
     * @param exception valid 검사 실패 예외
     * @return 필드별 에러 메시지를 포함한 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Http 상태 예외를 처리한다.
     *
     * @param exception HTTP 상태 예외
     * @return 예외 정보와 상태 코드가 포함된 응답
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException exception) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", exception.getStatusCode().value());
        errors.put("error", exception.getReason());

        return ResponseEntity.status(exception.getStatusCode()).body(errors);
    }

    /**
     * DB 무결성 제약 조건을 위반할 경우 예외를 처리한다.
     * @param exception DB 무결성 위반 예외
     * @return 예외 정보와 상태 코드가 포함된 응답
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", HttpStatus.CONFLICT.value());
        errors.put("error", HttpStatus.CONFLICT.getReasonPhrase());
        errors.put("message", exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errors);
    }
}
