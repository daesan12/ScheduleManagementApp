package com.example.schedulemanagementapp.Service;

import com.example.schedulemanagementapp.dto.ScheduleRequestDto;
import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.dto.UserRequestDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;

import java.util.List;

public interface ScheduleService {

    public ScheduleResponseDto findScheduleById(Long id);

    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    public UserResponseDto saveUser(UserRequestDto dto);
    public List<ScheduleResponseDto> findAllSchedules(String modifiedDate,String userName);
    public ScheduleResponseDto updateSchedule(Long id, String work,String userName,String password);
    void deleteSchedule(Long id,String password);
}
