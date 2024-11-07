package com.example.schedulemanagementapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String password;
    private String work;
    private String userId;
    private String userName;
    private String schedulesDate;
    private String createdDate;
    private String modifiedDate;

    private String email;
    private LocalDateTime create_date;

    public Schedule(Long id, String work, String user_id, String user_name, String schedules_date, String created_date, String modified_date) {
        this.id = id;
        this.password = password;
        this.work = work;
        this.userId = user_id;
        this.userName = user_name;
        this.schedulesDate = schedules_date;
        this.createdDate = created_date;
        this.modifiedDate = modified_date;
    }

    public Schedule(String userId, String work, String password, String userName, String schedulesDate) {
        this.work = work;
        this.password = password;
        this.userId = userId;
        this.userName = userName;
        this.schedulesDate = schedulesDate;
    }


}
