package com.example.schedulemanagementapp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice 
public class GlobalExceptionHandler {

  // ResponseStatusException 처리
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("status", String.valueOf(ex.getStatusCode().value()));  // HTTP 상태 코드
    errorResponse.put("error", ex.getReason());  // 지정한 에러 메시지

    return new ResponseEntity<>(errorResponse, ex.getStatusCode());
  }

}