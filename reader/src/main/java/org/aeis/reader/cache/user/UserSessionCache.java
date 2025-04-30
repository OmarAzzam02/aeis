package org.aeis.reader.cache.user;


import org.aeis.reader.dto.TokenInfoDto;
import org.aeis.reader.dto.userdto.UserDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserSessionCache {




    @Autowired
    private TokenCache tokenCache;

    @Autowired
    private ConcurrentHashMap<TokenInfoDto, UserDTO> userSessionCache;

    Logger log = org.slf4j.LoggerFactory.getLogger(UserSessionCache.class);



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



    public boolean isValidUser(String token){
        try {
            TokenInfoDto tokenInfo = tokenCache.getTokenFromCache(token);
             if (isTokenValid(tokenInfo))
                 return true;
            return false;

        }catch (Exception e) {
            log.error("Error while validating user In userSessionCache : {}", e.getMessage());
            e.printStackTrace();

        }
        return false;
    }


    public UserDTO getUserFromToken(String token) {
        TokenInfoDto tokenInfo = tokenCache.getTokenFromCache(token);
        return getUserFromSessionCache(tokenInfo);
    }



}
