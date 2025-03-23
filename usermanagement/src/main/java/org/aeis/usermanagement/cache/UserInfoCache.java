package org.aeis.usermanagement.cache;


import lombok.Getter;
import org.aeis.usermanagement.dto.UserDTO;
import org.aeis.usermanagement.entity.User;
import org.aeis.usermanagement.service.mapper.UserMapperService;
import org.aeis.usermanagement.service.user.UserInfoRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Getter
public class UserInfoCache{
    @Autowired
    private Map<Long , UserDTO> userInfoCache;

    @Autowired
    private UserInfoRetrievalService userInfoRetrievalService;


    @Async
     public void addUserInfoToCache(User user){
        UserDTO userDTO = userInfoRetrievalService.getUserInfo(user.getId());
        userInfoCache.putIfAbsent(userDTO.getId(), userDTO);
    }

    public UserDTO getUserInfoFromCache(Long userId){
        return userInfoCache.get(userId);
    }



}
