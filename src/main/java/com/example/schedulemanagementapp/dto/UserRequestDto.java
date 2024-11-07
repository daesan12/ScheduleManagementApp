package com.example.schedulemanagementapp.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRequestDto {
    private String userId;
    private String userName;
    private String email;

}
