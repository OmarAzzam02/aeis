package org.aeis.usermanagement.dto.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.aeis.usermanagement.dto.UserDTO;

@Builder
public class UserSessionDto {

    @JsonProperty("user_info")
    private UserDTO userInfo;

    @JsonProperty("token_info")
    private TokenInfoDto tokenInfo;

    public TokenInfoDto getTokenInfo() {
        return tokenInfo;
    }

    public UserDTO getUserInfo() {
        return userInfo;
    }
}
