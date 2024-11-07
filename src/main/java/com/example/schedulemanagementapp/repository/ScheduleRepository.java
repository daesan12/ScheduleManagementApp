package com.example.schedulemanagementapp.repository;

import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    UserResponseDto saveUser(User user);

    List<ScheduleResponseDto> findAllSchedules(String modifiedDate,String userName);
}