package com.example.schedulemanagementapp.dto;

import com.example.schedulemanagementapp.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String userId;
    //   private String password;
    private String work;
    private String userName;
    private String schedulesDate;
    private String createdDate;
    private String modifiedDate;

    //일정 조회 데이터
    public ScheduleResponseDto(Schedule schedule) {

        this.work = schedule.getWork();
        this.userId = schedule.getUserId();
        this.userName = schedule.getUserName();
        this.schedulesDate = schedule.getSchedulesDate();
        this.id = schedule.getId();
        this.modifiedDate = schedule.getModifiedDate();
        this.createdDate = schedule.getCreatedDate();
    }

    //일정생성 후 반환 데이터
    public ScheduleResponseDto(String work, String userId, String userName, String schedulesDate) {
        this.work = work;
        this.userId = userId;
        this.userName = userName;
        this.schedulesDate = schedulesDate;
    }
}
