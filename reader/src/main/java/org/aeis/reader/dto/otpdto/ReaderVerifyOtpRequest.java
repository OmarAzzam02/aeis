package org.aeis.reader.dto.otpdto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReaderVerifyOtpRequest {

    @JsonProperty("otp")
    private String otp;


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
