package org.aeis.reader.cache;


import org.aeis.reader.dto.TokenInfoDto;
import org.aeis.reader.dto.userdto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserSessionCache {




    @Autowired
    private ConcurrentHashMap<TokenInfoDto, UserDTO> userSessionCache;


    public ConcurrentHashMap<TokenInfoDto, UserDTO> getUserSessionCache() {
        return userSessionCache;
    }


    public void addToSessionCache(TokenInfoDto tokenInfoDto, UserDTO userDTO) {
        userSessionCache.put(tokenInfoDto, userDTO);
    }

    public UserDTO getUserFromSessionCache(TokenInfoDto tokenInfoDto) {
        return userSessionCache.get(tokenInfoDto);
    }

    public void removeFromSessionCache(TokenInfoDto tokenInfoDto) {
        userSessionCache.remove(tokenInfoDto);
    }

    public boolean isTokenValid(TokenInfoDto tokenInfoDto) {
        return userSessionCache.containsKey(tokenInfoDto);
    }




}
