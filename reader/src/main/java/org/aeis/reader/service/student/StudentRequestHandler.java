package org.aeis.reader.service.student;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.cache.ValidateCacheData;
import org.aeis.reader.dto.usersettingsdto.UserSettingDTO;
import org.aeis.reader.util.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class StudentRequestHandler {


    @Autowired
    private ValidateTokenService validateTokenService;

    @Value("${tts.web.socket.connect.url}")
    private String ttsWebSocketConnectUrl;

    @Value("${stt.web.socket.connect.url}")
    private String sttWebSocketConnectUrl;

    public ResponseEntity<?> connectToLecture(UserSettingDTO userSettingDTO , String token){

        // check if the token is valid
        if (!validateTokenService.checkTokenValidity(token))
         return ResponseEntity.status(401).body("Token is not valid");

        // check if the user has a lecture in this time
        return connectToServices(userSettingDTO);

    }

    private ResponseEntity<?> connectToServices(UserSettingDTO userSettingDTO) {
        return userSettingDTO.isSttRequestEnabled() ? connectToSTTService() : connectToTTSService();
    }

    private ResponseEntity<?> connectToTTSService() {
        return ResponseEntity.created(URI.create(ttsWebSocketConnectUrl)).build();
    }

    private ResponseEntity<URI> connectToSTTService() {
        return ResponseEntity.created(URI.create(sttWebSocketConnectUrl)).build();
    }
}
