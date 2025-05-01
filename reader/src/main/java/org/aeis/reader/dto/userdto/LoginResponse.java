package org.aeis.reader.dto.userdto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Setter
@Getter
@Builder
public class LoginResponse {


    @JsonProperty("role")
    private String role;
    @JsonProperty("token")
    private String token;
    @JsonProperty("message")
    private String message;
}
