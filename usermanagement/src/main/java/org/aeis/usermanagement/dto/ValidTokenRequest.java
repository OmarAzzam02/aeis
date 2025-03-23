package org.aeis.usermanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidTokenRequest {

    @JsonProperty("token")
    private String token;

    public String getToken() {
        return token;
    }
}

