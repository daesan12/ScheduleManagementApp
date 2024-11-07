package com.example.schedulemanagementapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRequestDto {
    private String userId;
    @NotBlank(message = "이름은 필수입력 값입니다")
    private String userName;
    @NotBlank(message = "이메일은 필수값입니다")
    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;

}
