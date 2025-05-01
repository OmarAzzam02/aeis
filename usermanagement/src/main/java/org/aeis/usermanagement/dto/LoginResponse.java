package org.aeis.usermanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @JsonProperty("role")
    private String role;
    @JsonProperty("token")
    private String token;
    @JsonProperty("message")
    private String message;
}
