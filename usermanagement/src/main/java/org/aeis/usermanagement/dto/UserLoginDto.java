package org.aeis.usermanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UserLoginDto {

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

}
