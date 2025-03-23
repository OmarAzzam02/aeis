package org.aeis.usermanagement.dto.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
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
