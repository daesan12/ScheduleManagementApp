package com.example.schedulemanagementapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private  String userId;
    private  String userName;
    private  String email;
   // private LocalDateTime createDate;



    public User(String userId,String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        //this.createDate = LocalDateTime.now();
    }

}
