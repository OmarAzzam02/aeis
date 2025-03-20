package org.aeis.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {
    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

}
