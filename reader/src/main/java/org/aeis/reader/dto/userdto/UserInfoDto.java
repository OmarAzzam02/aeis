package org.aeis.reader.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserInfoDto implements UserDTO {
    @JsonProperty("user_id")
    private long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
   private Role role;

    @Override
    public Long getId() {
         return this.id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public Set<CourseDto> getCourses() {
        return Set.of();
    }
}
