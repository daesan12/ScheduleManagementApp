package com.example.schedulemanagementapp.Service;

import com.example.schedulemanagementapp.dto.ScheduleRequestDto;
import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.dto.UserRequestDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.entity.User;
import com.example.schedulemanagementapp.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

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
    public List<ScheduleResponseDto> findAllSchedules(String modifiedDate,String userName,int page, int size ) {
        List<ScheduleResponseDto> schedule = scheduleRepository.findAllSchedules(modifiedDate,userName,page,size);
        return scheduleRepository.findAllSchedules(modifiedDate,userName,page,size);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String work, String userName, String password) {
        //필수 입력값 검증
        if(id == null || password == null || work == null || userName == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "아이디, 비밀번호, 일정, 유저이름은 필수값입니다");
        }
        //사용자 이름 길이 검증
        if (userName.length() > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "사용자 이름은 10자를 초과할 수 없습니다.");
        }

        //스케줄 업데이트 시도
        int updateRow = scheduleRepository.updateSchedule(id,work,userName,password);
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "스케줄을 찾을 수 없거나 비밀번호가 일치하지 않습니다.");
        }
        //업데이트 된 스케줄 조회
        Schedule schedule = scheduleRepository.findScheduleByIdorElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        //스케줄 삭제
        int deletedRow = scheduleRepository.deleteScheduleByid(id,password);

        if(deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지않거나 아이디가 존재하지않습니다"+id);
        }
    }
}