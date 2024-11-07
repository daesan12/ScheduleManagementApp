package com.example.schedulemanagementapp.repository;

import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class jdbcTemplateScheduleRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public jdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        jdbcInsert.withTableName("schedules")
                .usingColumns("password","work","user_id","user_name","schedules_date");
System.out.println("유저아이디:"+schedule.getUserId()+"\n비번:"+schedule.getPassword()+"\n유저이름은"+schedule.getUserName()+"\n일정:"+schedule.getWork()+"\n날짜"+schedule.getSchedulesDate());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("password", schedule.getPassword());
        parameters.put("work", schedule.getWork());
        parameters.put("user_id", schedule.getUserId());
        parameters.put("user_name", schedule.getUserName());
        parameters.put("schedules_date", schedule.getSchedulesDate());
        jdbcInsert.execute(parameters);

        return new ScheduleResponseDto(schedule.getWork(),schedule.getUserId(),schedule.getUserName(),schedule.getSchedulesDate());
    }



    @Override
    public UserResponseDto saveUser(User user) {

       //String sql ="insert into userinfo (user_id, user_name, email) values (?,?,?)";
      // jdbcTemplate.execute(sql, user.getUserId(),user.getUserName(),user.getEmail());
        //String sql = String.format("insert into userinfo (user_id, user_name, email) VALUES ('%s', '%s', '%s')", user.getUserId(), user.getUserName(),user.getEmail());
        //jdbcTemplate.execute(sql);
        //System.out.println();
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("userinfo")
                .usingColumns("user_id","user_name","email");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", user.getUserId());
        parameters.put("user_name", user.getUserName());
        parameters.put("email", user.getEmail());
        jdbcInsert.execute(parameters);
        return new UserResponseDto(user.getUserId(), user.getEmail(), user.getUserName());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String modifiedDate,String userName) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id,user_id,work,user_name,schedules_date,created_date,modified_date from schedules ");

            if(StringUtils.hasText(modifiedDate)||StringUtils.hasText(userName)) {
                sb.append("where DATE(modified_date)=? OR user_name =?");
                return jdbcTemplate.query(sb.toString(), saveScheduleRowMapper(),modifiedDate ,userName );
            }

        return jdbcTemplate.query(sb.toString(), saveScheduleRowMapper() );
    }

    private RowMapper<ScheduleResponseDto> saveScheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("user_id"),
                        rs.getString("work"),
                        rs.getString("schedules_date"),
                        rs.getString("user_name"),
                        rs.getString("created_date"),
                        rs.getString("modified_date")
                );
            }
        };

    }
}