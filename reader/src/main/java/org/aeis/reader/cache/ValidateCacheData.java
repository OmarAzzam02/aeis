package org.aeis.reader.cache;


import org.aeis.reader.dto.TokenInfoDto;
import org.aeis.reader.dto.userdto.UserDTO;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Component
public class ValidateCacheData {

     private static final Log log = LogFactory.getLog(ValidateCacheData.class);
    @Autowired
    private TokenCache tokenCache;

    @Autowired
    private UserSessionCache userSessionCache;



    @Scheduled(fixedDelay = 6000)
    public void validateCache() {
        log.info("Checking for expired tokens in Validate Cache Data");
        checkExpiredTokens();
    }


    private void checkExpiredTokens() {

        userSessionCache.getUserSessionCache().entrySet().removeIf(entry -> {
            TokenInfoDto tokenInfo = entry.getKey();
            boolean expired = tokenInfo.getRemainingTimeMillis() <= 0;

            if (!expired)
                return false;


            tokenCache.removeFromTokenCache(tokenInfo.getToken());
            return true;
        });
    }


}
