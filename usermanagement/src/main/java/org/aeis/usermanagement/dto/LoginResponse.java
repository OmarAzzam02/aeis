package org.aeis.usermanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("token")
    private String token;
    @JsonProperty("message")
    private String message;
}
