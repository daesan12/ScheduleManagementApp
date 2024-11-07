package com.example.schedulemanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class ScheduleRequestDto {
    @NotBlank(message = "비밀번호는 필수값입니다")
    private String password;
    @NotBlank(message = "할 일은 필수값입니다")
    @Size(max= 200, message = "할 일은 최대 200자 이내로 입력해야 합니다")
    private String work;
    private String userId;
    private String userName;
    private String schedulesDate;
    private String createdDate;
    private String modifiedDate;




}
