package org.aeis.reader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.aeis.reader.deserializer.UserDtoDeserializer;
import org.aeis.reader.dto.userdto.UserDTO;
import org.aeis.reader.dto.userdto.UserInfoDto;

import java.io.Serializable;

public class UserSessionDto  implements Serializable {

    @JsonProperty("user_info")
    @JsonDeserialize(using = UserDtoDeserializer.class)
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
