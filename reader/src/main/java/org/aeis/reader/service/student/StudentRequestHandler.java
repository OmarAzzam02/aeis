package org.aeis.reader.service.student;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.cache.ValidateCacheData;
import org.aeis.reader.dto.usersettingsdto.UserSettingDTO;
import org.aeis.reader.util.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudentRequestHandler {


    @Autowired
    private ValidateTokenService validateTokenService;

    public ResponseEntity<?> connectToLecture(UserSettingDTO userSettingDTO , String token) {

        // check if the token is valid
        if (!validateTokenService.checkTokenValidity(token));
         return ResponseEntity.status(401).body("Token is not valid");
        // check the setting the user want
        if (userSettingDTO.isSttRequestEnabled()){
            connectToSTTService();
        }

        // connect to the services websockets

        return null;
    }

    private void connectToSTTService() {

    }
}
