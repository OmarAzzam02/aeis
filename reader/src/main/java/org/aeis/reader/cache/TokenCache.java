package org.aeis.reader.cache;


import org.aeis.reader.dto.TokenInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TokenCache {

    @Autowired
    private Map<String, TokenInfoDto> tokenCache;


    public void addToTokenCache(TokenInfoDto tokenInfoDto) {
        tokenCache.putIfAbsent(tokenInfoDto.getToken(), tokenInfoDto);
    }

    public TokenInfoDto getTokenFromCache(String token) {
        return tokenCache.get(token);
    }

    public void removeFromTokenCache(String token) {
        tokenCache.remove(token);
    }

    public boolean containsToken(String token) {
        return tokenCache.containsKey(token);
    }














}
