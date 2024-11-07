package com.example.schedulemanagementapp.dto;

import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String userId;
    private String userName;
    private String email;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.userName = user.getUserName();
    }
}
