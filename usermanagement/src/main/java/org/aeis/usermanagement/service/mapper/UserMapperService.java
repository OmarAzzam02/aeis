package org.aeis.usermanagement.service.mapper;


import org.aeis.usermanagement.dto.*;
import org.aeis.usermanagement.entity.Course;
import org.aeis.usermanagement.entity.CourseTimePeriod;
import org.aeis.usermanagement.entity.Hall;
import org.aeis.usermanagement.entity.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserMapperService {

    public UserStudentInfo mapUserToStudent(User user){
         return UserStudentInfo.builder()
                .userInfo(UserInfoDto.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .build();
    }

    public UserInstructorInfo mapUserToInstructor(User user) {
        return UserInstructorInfo.builder()
                .userInfo(UserInfoDto.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .build();
    }

    public UserInfoDto mapUserToAdmin(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }



    public Set<CourseDto> mapCourses(Set<Course> courses) {
        return courses.stream()
                .map(course -> CourseDto.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .section(course.getSection())
                        .hall(mapHallToDto(course.getHall()))
                        .courseTimePeriod(mapTimePeriodToDto(course.getCourseTimePeriod()))
                        .build())
                .collect(Collectors.toSet());
    }

    private HallDto mapHallToDto(Hall hall) {
        return HallDto.builder()
                .id(hall.getId())
                .name(hall.getName())
                .build();
    }

    private CourseTimePeriodDto mapTimePeriodToDto(CourseTimePeriod timePeriod) {
        return CourseTimePeriodDto.builder()
                .days(timePeriod.getDays())
                .startTime(timePeriod.getStartTime())
                .endTime(timePeriod.getEndTime())
                .build();
    }
}
