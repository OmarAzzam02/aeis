package org.aeis.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.aeis.usermanagement.entity.Role;

import java.util.Set;



@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserInstructorInfo  implements UserDTO{
    @JsonProperty("user_info")
    UserInfoDto userInfo;
    @JsonProperty("assigned_courses")
    private Set<CourseDto> assignedCourses;


}
