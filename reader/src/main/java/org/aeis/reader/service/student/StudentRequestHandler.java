package org.aeis.reader.service.student;


import  org.aeis.reader.dto.video.VideoDTO;
import org.aeis.reader.cache.user.TokenCache;
import org.aeis.reader.cache.user.UserSessionCache;
import org.aeis.reader.dto.TokenInfoDto;
import org.aeis.reader.dto.UserSessionDto;
import org.aeis.reader.dto.course.CourseRequest;
import org.aeis.reader.dto.userdto.CourseDto;
import org.aeis.reader.dto.userdto.UserDTO;
import org.aeis.reader.dto.usersettingsdto.UserSettingDTO;
import org.aeis.reader.util.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Service
public class StudentRequestHandler {


    @Autowired
    private ValidateTokenService validateTokenService;

    @Value("${tts.web.socket.connect.url}")
    private String ttsWebSocketConnectUrl;

    @Value("${stt.web.socket.connect.url}")
    private String sttWebSocketConnectUrl;

    @Autowired
    private RedirectStudentService redirectStudentService;

    @Autowired
    private TokenCache tokenCache;
    @Autowired
    private UserSessionCache userSessionCache;



    public ResponseEntity<?> connectToLecture(String hallId , UserSettingDTO userSettingDTO , String token){

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

    public ResponseEntity<?> getSummaries(String token) {
        // check if the token is valid
        UserDTO userDTO = getUserDTO(token);
        CourseRequest courseRequest = buildCourseRequest(userDTO.getCourses());
      return redirectStudentService.redirectToSummaryService(courseRequest);


    }


    private CourseRequest buildCourseRequest(Set<CourseDto> courseDto) {

        List<Long> courseIds = courseDto.stream()
                .map(CourseDto::getId)
                .toList();

        return CourseRequest.builder()
                .courseIds(courseIds)
                .build();
    }


    public ResponseEntity<List<VideoDTO>> getVideos(String token) {

        UserDTO userDTO = getUserDTO(token);


        CourseRequest courseRequest = buildCourseRequest(userDTO.getCourses());

        return redirectStudentService.redirectToVideoService(courseRequest);

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
