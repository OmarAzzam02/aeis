package org.aeis.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminDto implements  UserDTO{
    @JsonProperty("user_info")
    private UserInfoDto userInfo;

    @Override
    public Long getId() {
        return userInfo.getId();
    }
}
