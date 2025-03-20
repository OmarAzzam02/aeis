package org.aeis.usermanagement.dto;


import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private boolean status;
    private String token;
    private String message;


}
