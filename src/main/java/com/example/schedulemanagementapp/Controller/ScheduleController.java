package com.example.schedulemanagementapp.Controller;


import com.example.schedulemanagementapp.Service.ScheduleService;
import com.example.schedulemanagementapp.dto.ScheduleRequestDto;
import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.dto.UserRequestDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/Schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;


    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //일정 생성
    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    //회원 가입
    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto) {

        return new ResponseEntity<>(scheduleService.saveUser(dto), HttpStatus.CREATED);
    }

    //유저이름이나 수정일기준으로 찾기 페이지 처리 되어있음
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(@RequestParam(value = "modifiedDate", required = false) String modifiedDate,
                                                      @RequestParam(value = "userName", required = false) String userName,
                                                      @RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        System.out.println("페이지 번호=" + page + ", 사이즈=" + size);
        return scheduleService.findAllSchedules(modifiedDate, userName, page, size);
    }

    //일정 고유 아이디로 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable(value = "id") @Valid Long id) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    //일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable(value = "id") Long id, @Valid @RequestBody ScheduleRequestDto dto) {


        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getWork(), dto.getUserName(), dto.getPassword()), HttpStatus.OK);
    }

    //일정 고유 아이디로 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable(value = "id") Long id, @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteSchedule(id, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
