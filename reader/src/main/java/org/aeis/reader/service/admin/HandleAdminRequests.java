package org.aeis.reader.service.admin;


import org.aeis.reader.cache.user.TokenCache;
import org.aeis.reader.cache.user.UserSessionCache;
import org.aeis.reader.dto.TokenInfoDto;
import org.aeis.reader.dto.userdto.Role;
import org.aeis.reader.dto.userdto.UserDTO;
import org.aeis.reader.util.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HandleAdminRequests {


    @Autowired
    private ValidateTokenService validateTokenService;
    @Autowired
    private TokenCache tokenCache;
    @Autowired
    private UserSessionCache userSessionCache;

    @Autowired
    private RedirectAdminService redirectAdminService;


    public ResponseEntity<?> getDeviceStatus(String token) {

        try {
            UserDTO userDTO = getUserDTO(token);

//            if (!userDTO.getRole().equals(Role.ADMIN))
//                return ResponseEntity.status(403).body("User is not authorized to access this resource");

            return redirectAdminService.getDevicesStatus();
        } catch (Exception e) {
             return ResponseEntity.status(401).body(e.getMessage());
        }


    }













    private UserDTO getUserDTO(String token) {
        if (!validateTokenService.checkTokenValidity(token))
            throw new RuntimeException("Token is not valid");


        TokenInfoDto tokenInfo = tokenCache.getTokenFromCache(token);
        UserDTO userDTO = userSessionCache.getUserFromSessionCache(tokenInfo);
        if (userDTO == null) {
            throw new RuntimeException("User not found in session cache");
        }
        return userDTO;
    }



}
