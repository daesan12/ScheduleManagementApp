package com.example.schedulemanagementapp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// 비밀번호 불일치 예외
public class InvalidPasswordException extends ResponseStatusException {
    public InvalidPasswordException(String message) {
        super(HttpStatus.UNAUTHORIZED,message);
    }
}