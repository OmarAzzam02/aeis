package org.aeis.usermanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.aeis.usermanagement.entity.Course;
import org.aeis.usermanagement.entity.Role;

import java.util.List;
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
}
