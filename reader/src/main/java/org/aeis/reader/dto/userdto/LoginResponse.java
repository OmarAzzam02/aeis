package org.aeis.reader.dto.userdto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {


    public LoginResponse(String firstName, String token, String message) {
        this.firstName = firstName;
        this.token = token;
        this.message = message;
    }

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("token")
    private String token;
    @JsonProperty("message")
    private String message;
}
