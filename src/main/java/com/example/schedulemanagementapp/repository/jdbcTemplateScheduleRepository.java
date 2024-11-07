package com.example.schedulemanagementapp.repository;

import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.entity.User;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class jdbcTemplateScheduleRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ProjectInfoAutoConfiguration projectInfoAutoConfiguration;

    public jdbcTemplateScheduleRepository(DataSource dataSource, ProjectInfoAutoConfiguration projectInfoAutoConfiguration) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.projectInfoAutoConfiguration = projectInfoAutoConfiguration;
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
    public List<ScheduleResponseDto> findAllSchedules(String modifiedDate,String userName,int page,int size) {
        System.out.println("페이지 번호="+page+", 사이즈="+size);
        int offset = (page - 1) * size;
        List<Object> params = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("select id,user_id,work,user_name,schedules_date,created_date,modified_date from schedules ");

            if(StringUtils.hasText(modifiedDate)||StringUtils.hasText(userName)) {
                sb.append("where ");
                if(StringUtils.hasText(modifiedDate)) {
                    sb.append("DATE(modified_date) = ? ");
                    params.add(modifiedDate);
                }
                if(StringUtils.hasText(userName)){
                    if (!params.isEmpty()) sb.append("OR ");
                    sb.append("user_name = ? ");
                    params.add(userName);
                }

            }
        sb.append("LIMIT ? OFFSET ?");
            params.add(size);
            params.add(offset);

        return jdbcTemplate.query(sb.toString(),params.toArray() ,ScheduleRowMapperV1() );
    }

    @Override
    public Schedule findScheduleByIdorElseThrow(Long id) {

        List<Schedule> result = jdbcTemplate.query("select id,user_id,work,user_name,schedules_date,created_date,modified_date from schedules where id = ? ", ScheduleRowMapperV2(),id);

        return result.stream().findAny().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"없는 아이디입니다"+id));
    }

    @Override
    public int updateSchedule(Long id, String work, String userName, String password) {
        return jdbcTemplate.update("update schedules set work= ?, user_name= ? where  id = ? and password= ?" ,work,userName,id,password);
    }

    @Override
    public int deleteScheduleByid(Long id, String password) {
        return jdbcTemplate.update("delete from schedules where id = ? and password = ?" ,id,password);
    }

    private RowMapper<ScheduleResponseDto> ScheduleRowMapperV1() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("user_id"),
                        rs.getString("work"),
                        rs.getString("user_name"),
                        rs.getString("schedules_date"),
                        rs.getString("created_date"),
                        rs.getString("modified_date")
                );
            }
        };

    }


    private RowMapper<Schedule> ScheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("work"),
                        rs.getString("user_id"),
                        rs.getString("user_name"),
                        rs.getString("schedules_date"),
                        rs.getString("created_date"),
                        rs.getString("modified_date")
                );
            }
        };

    }
}
