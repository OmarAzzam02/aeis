package org.aeis.twoauthfactor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyOtpRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("otp")
    private String otp;

    public String getOtp() {
        return otp;
    }

    public String getEmail() {
        return email;
    }


}
