package com.example.schedulemanagementapp.Controller;


import com.example.schedulemanagementapp.Service.ScheduleService;
import com.example.schedulemanagementapp.dto.ScheduleRequestDto;
import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.dto.UserRequestDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Schedules")
public class ScheduleController {
private final ScheduleService scheduleService;


public ScheduleController(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
}

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto){
    return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }


    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto){

        return new ResponseEntity<>(scheduleService.saveUser(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(@RequestParam(value="modifiedDate",required = false)String modifiedDate,
            @RequestParam(value="userName",required = false) String userName,
            @RequestParam(value="page", defaultValue = "1")int page,
            @RequestParam(value="size", defaultValue = "10")int size){
        System.out.println("페이지 번호="+page+", 사이즈="+size);
        return scheduleService.findAllSchedules(modifiedDate,userName,page,size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable(value="id",required = false)Long id){
    return new ResponseEntity<>(scheduleService.findScheduleById(id),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable(value="id",required = false)Long id,@RequestBody ScheduleRequestDto dto){
        return new ResponseEntity<>(scheduleService.updateSchedule(id,dto.getWork(),dto.getUserName(),dto.getPassword()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable(value="id",required = false)Long id,@RequestBody ScheduleRequestDto dto){
        scheduleService.deleteSchedule(id,dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
