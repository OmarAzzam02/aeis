package org.aeis.usermanagement.service.mapper;


import org.aeis.usermanagement.dto.UserDTO;
import org.aeis.usermanagement.dto.UserInfoDto;
import org.aeis.usermanagement.dto.UserInstructorInfo;
import org.aeis.usermanagement.dto.UserStudentInfo;
import org.aeis.usermanagement.entity.User;
import org.springframework.stereotype.Service;


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
}
