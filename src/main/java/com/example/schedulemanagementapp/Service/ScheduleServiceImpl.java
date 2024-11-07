package com.example.schedulemanagementapp.Service;

import com.example.schedulemanagementapp.dto.ScheduleRequestDto;
import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.dto.UserRequestDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.entity.User;
import com.example.schedulemanagementapp.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findScheduleByIdorElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getUserId(),dto.getWork(),dto.getPassword(), dto.getUserName(),dto.getSchedulesDate());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto dto) {

        User user = new User(dto.getUserId(), dto.getUserName(), dto.getEmail());
        return scheduleRepository.saveUser(user);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String modifiedDate,String userName) {
        List<ScheduleResponseDto> schedule = scheduleRepository.findAllSchedules(modifiedDate,userName);
        return scheduleRepository.findAllSchedules(modifiedDate,userName);
    }
}
