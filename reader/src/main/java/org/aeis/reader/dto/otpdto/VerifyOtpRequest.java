package org.aeis.reader.dto.otpdto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyOtpRequest {

    @JsonProperty("otp")
    private String otp;


    @JsonProperty("email")
    private String email;

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public String getOtp() {
        return otp;
    }
}
