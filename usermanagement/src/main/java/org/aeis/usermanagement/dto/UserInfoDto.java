package org.aeis.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.aeis.usermanagement.entity.Role;


@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
