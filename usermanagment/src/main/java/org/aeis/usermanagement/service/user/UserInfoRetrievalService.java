package org.aeis.usermanagement.service.user;


import lombok.extern.log4j.Log4j2;
import org.aeis.usermanagement.dao.UserDao;
import org.aeis.usermanagement.dto.*;
import org.aeis.usermanagement.entity.User;
import org.aeis.usermanagement.service.mapper.UserMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;



@Log4j2
@Service
public class UserInfoRetrievalService {

    @Autowired
    private UserDao db;

    @Autowired
    private UserMapperService mapperService;


    public UserDTO getUserInfo(Long userId) {

        try {
            Optional<User> user = db.findById(userId);

            if (!user.isPresent())
                throw new RuntimeException("User Info not found");

            switch (user.get().getRole()) {
                case STUDENT -> {
                    return getUserStudentInfo(user);
                }



                case INSTRUCTOR -> {
                    return getUserInstructorInfo(user);

                }
                case ADMIN -> {
                    return getUserInfoDto(user);
                }


            }
        } catch (Exception e) {
            log.error("Error while getting user info {} ", e.getMessage());
            throw new RuntimeException("Error while getting user info");

        }

        return null;
    }

    private UserInfoDto getUserInfoDto(Optional<User> user) {
        try {
            log.info("Getting admin info for user id: {}", user.get().getId());
            return getAdminInfo(user.get());
        }catch (Exception e){
            log.error("Error while getting admin info {} ", e.getMessage());
            throw new RuntimeException("Error while getting admin info");
        }
    }

    private UserInstructorInfo getUserInstructorInfo(Optional<User> user) {
        try {
            return getInstructorInfo(user.get());
        } catch (Exception e) {
            log.error("Error while getting instructor info {} ", e.getMessage());
            throw new RuntimeException("Error while getting instructor info");
        }
    }

    private UserStudentInfo getUserStudentInfo(Optional<User> user) {
        try {
            return getStudentInfo(user.get());
        } catch (Exception e) {
            log.error("Error while getting student info {} ", e.getMessage());
            throw new RuntimeException("Error while getting student info");

        }
    }

    private UserInfoDto getAdminInfo(User user) {
        return mapperService.mapUserToAdmin(user);
    }


    public UserStudentInfo getStudentInfo(User user) {

        try {
            log.info("Getting student courses for user id: {}", user.getId());
            UserStudentInfo stdInfo = mapperService.mapUserToStudent(user);

            Set<CourseDto> stdCourses =  db.findCoursesByUserId(user.getId());
            stdInfo.setRegisteredCourses(stdCourses);

            return stdInfo;
        }catch (Exception e){
            log.error("Error while getting student courses {} " ,  e.getMessage());
            throw new RuntimeException("Error while getting student courses");
        }
    }


    public UserInstructorInfo getInstructorInfo(User user) {
        try {
            UserInstructorInfo instructorInfo = mapperService.mapUserToInstructor(user);

            Set<CourseDto> instructorCourses = db.findInstructorAssignedCoursesByUserId(user.getId());
            instructorInfo.setAssignedCourses(instructorCourses);

            return instructorInfo;

        } catch (Exception e) {
            throw new RuntimeException("Error while getting instructor info");
        }
    }





}
