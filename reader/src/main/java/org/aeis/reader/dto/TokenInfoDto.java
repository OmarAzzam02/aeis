package org.aeis.reader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenInfoDto {

    @JsonProperty("token")
    private String token;

    @JsonProperty("expire_date")
    long expireDate;

    public String getToken() {
        return token;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public long getRemainingTimeMillis() {
        return Math.max(expireDate - System.currentTimeMillis(), 0);
    }
}
