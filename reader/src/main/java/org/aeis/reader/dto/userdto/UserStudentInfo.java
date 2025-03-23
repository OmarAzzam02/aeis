package org.aeis.reader.dto.userdto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserStudentInfo implements UserDTO{

    @JsonProperty("user_info")
    private UserInfoDto userInfo;

    @JsonProperty("registered_courses")
    private Set<CourseDto> registeredCourses;


    @Override
    public Long getId() {
        return userInfo.getId();
    }

    @Override
    public String getFirstName() {
        return userInfo.getFirstName();
    }

    @Override
    public String getLastName() {
        return userInfo.getLastName();
    }

    @Override
    public String getEmail() {
        return userInfo.getEmail();
    }

    @Override
    public Role getRole() {
        return userInfo.getRole();
    }

    @Override
    public Set<CourseDto> getCourses() {
        return registeredCourses;
    }
}
