package org.aeis.reader.util;


import org.aeis.reader.cache.user.TokenCache;
import org.aeis.reader.cache.user.UserSessionCache;
import org.aeis.reader.dto.UserSessionDto;
import org.aeis.reader.service.handler.UrlServiceLocator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ValidateTokenService {
    Logger log = org.slf4j.LoggerFactory.getLogger(ValidateTokenService.class);

    @Autowired
    private TokenCache tokenCache;

    @Autowired
    private UrlServiceLocator urlServiceLocator;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserSessionCache userSessionCache;

    public boolean checkTokenValidity(String token) {

        try {

            if(isInCache(token))
               return true;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<UserSessionDto> response = restTemplate.postForEntity(urlServiceLocator.getValidateTokenServiceUrl(), entity, UserSessionDto.class);
            synchronizeCache(Objects.requireNonNull(response.getBody()));

            return response.getStatusCode().equals(HttpStatus.OK);

        }catch (Exception e) {
            log.info("Error while validating token: {}");
            e.printStackTrace();
            return false;
        }


    }
    private void synchronizeCache(UserSessionDto userSessionDto) {
        tokenCache.addToTokenCache(userSessionDto.getTokenInfo());
        userSessionCache.addToSessionCache(userSessionDto.getTokenInfo(), userSessionDto.getUserInfo());
    }

    private boolean isInCache(String token) {
        return tokenCache.containsToken(token);
    }
}
