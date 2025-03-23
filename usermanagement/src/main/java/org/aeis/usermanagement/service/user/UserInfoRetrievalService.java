package org.aeis.usermanagement.service.user;

import lombok.extern.log4j.Log4j2;
import org.aeis.usermanagement.dao.UserDao;
import org.aeis.usermanagement.dto.*;
import org.aeis.usermanagement.entity.Role;
import org.aeis.usermanagement.entity.User;
import org.aeis.usermanagement.service.mapper.UserMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Log4j2
@Service
public class UserInfoRetrievalService {

    private final UserDao db;
    private final UserMapperService mapperService;

    @Autowired
    public UserInfoRetrievalService(UserDao db, UserMapperService mapperService) {
        this.db = db;
        this.mapperService = mapperService;
    }

    private final Map<Role, Function<User, UserDTO>> roleBasedUserInfoMapper = Map.of(
            Role.STUDENT, this::getStudentInfo,
            Role.INSTRUCTOR, this::getInstructorInfo,
            Role.ADMIN, this::getAdminInfo
    );

    public UserDTO getUserInfo(Long userId) {
        User user = db.findById(userId)
                .orElseThrow(() -> null);

        return roleBasedUserInfoMapper.getOrDefault(user.getRole(), this::defaultUserInfo).apply(user);
    }

    private UserDTO defaultUserInfo(User user) {
        log.warn("Unhandled role for user ID {}: {}", user.getId(), user.getRole());
        return null;
    }

    private UserInfoDto getAdminInfo(User user) {
        log.info("Retrieving admin info for user ID: {}", user.getId());
        return mapperService.mapUserToAdmin(user);
    }

    private UserStudentInfo getStudentInfo(User user) {
        log.info("Retrieving student info for user ID: {}", user.getId());
        UserStudentInfo studentInfo = mapperService.mapUserToStudent(user);
        studentInfo.setRegisteredCourses(db.findCoursesByUserId(user.getId()));
        return studentInfo;
    }

    private UserInstructorInfo getInstructorInfo(User user) {
        log.info("Retrieving instructor info for user ID: {}", user.getId());
        UserInstructorInfo instructorInfo = mapperService.mapUserToInstructor(user);
        instructorInfo.setAssignedCourses(db.findInstructorAssignedCoursesByUserId(user.getId()));
        return instructorInfo;
    }
}
