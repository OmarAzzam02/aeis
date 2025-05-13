package org.aeis.reader.service.instructor;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.aeis.reader.cache.summary.GeneratedSummaryCache;
import org.aeis.reader.cache.user.UserSessionCache;
import org.aeis.reader.cache.video.GeneratedVideoCache;
import org.aeis.reader.dto.recording.RecordingDTO;
import org.aeis.reader.dto.recording.StopRecordingResponse;
import org.aeis.reader.dto.summary.GeneratedSummaryDTO;
import org.aeis.reader.dto.userdto.CourseDto;
import org.aeis.reader.dto.userdto.Role;
import org.aeis.reader.dto.userdto.UserDTO;
import org.aeis.reader.dto.video.GeneratedVideoDTO;
import org.aeis.reader.service.handler.UrlServiceLocator;
import org.aeis.reader.util.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Log4j2
public class LectureRecordingService {


    @Autowired
    private UrlServiceLocator urlServiceLocator;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserSessionCache userCache;

    @Autowired
    private ValidateTokenService tokenService;

    @Autowired
    private GeneratedSummaryCache summaryCache;


    @Autowired
    private GeneratedVideoCache videoCache;


    public ResponseEntity<?> startRecording(String hallConnectDTO, MultipartFile contextFile , String token) {

        try {
            if (!tokenService.checkTokenValidity(token))
                return ResponseEntity.badRequest().body("User Invalid Session found");


            UserDTO user = userCache.getUserFromToken(token);


            if (isAuthorizedToStartRecording(hallConnectDTO,user, contextFile)) {
                return ResponseEntity.ok("Recording Started");
            }

            return ResponseEntity.badRequest().body("You have no lecture to start recording");

        }catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }



    private boolean isAuthorizedToStartRecording(String hallConnectDTO,UserDTO user , MultipartFile contextFile) throws IOException {
        return  user.getRole().equals(Role.INSTRUCTOR)
                && userHasALecture(hallConnectDTO, user, contextFile);
    }

    public boolean userHasALecture(String hallConnectDTO , UserDTO user , MultipartFile contextFile) throws IOException {

        String today = LocalDate.now()
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .toLowerCase();

        LocalTime now = LocalTime.now().withNano(0);
        for (CourseDto course : user.getCourses()) {

            String days = course.getCourseTimePeriod().getDays().toLowerCase();
            List<String> daysList = Arrays.stream(days.split(","))
                    .map(String::trim)
                    .toList();


            if (daysList.contains(today.trim())) {
                LocalTime startTime = course.getCourseTimePeriod().getStartTime();
                LocalTime endTime =  course.getCourseTimePeriod().getEndTime();

                if (!now.isBefore(startTime)
                        && !now.isAfter(endTime)
                        && course.getHall().getName().
                        equals(hallConnectDTO.trim()))
                {
                    RecordingDTO recordingDTO = new RecordingDTO(
                            course.getHall().getId(),
                            course.getId(),
                            contextFile != null ? contextFile.getBytes() : null
                    );
                    sendStatusToStartRecording(recordingDTO);
                    return true;
                }
            }
        }
        return false;
    }




    public ResponseEntity<?> stopRecording(String hallId, String token) {
        if (!tokenService.checkTokenValidity(token))
            return ResponseEntity.badRequest().body("User Invalid Session found");

        UserDTO user = userCache.getUserFromToken(token);

        if (user.getRole().equals(Role.INSTRUCTOR) || user.getRole().equals(Role.ADMIN)) {


           return sendStatusToStopRecording(hallId);

        } else {
            return ResponseEntity.badRequest().body("You have no lecture to stop recording");
        }



    }

    private ResponseEntity<?> sendStatusToStopRecording(String hallId) {
        try {
            // send the context and the
            ResponseEntity<?> response = restTemplate.postForEntity(urlServiceLocator.getRECORDING_STOPPER_URL() + "/" + hallId, null, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {

                   return generatedSummaryAndVideo();

            } else {
                log.error("Failed to stop recording");
            }

            return response;

        } catch (Exception e) {
            log.error("Error while sending signal to model to stop recording ");
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while sending signal to model to stop recording");
        }
    }

    private ResponseEntity<?> generatedSummaryAndVideo() {
        summaryCache.clearCache();
        videoCache.clearCache();

        while(summaryCache.getLastGeneratedSummary() == null ||  videoCache.getLastVideoRecording() == null) {}

        GeneratedSummaryDTO generatedSummaryDTO = summaryCache.getLastGeneratedSummary();
        GeneratedVideoDTO   generatedVideoDTO = videoCache.getLastVideoRecording();

        StopRecordingResponse stopRecordingResponse = StopRecordingResponse.builder()
                .recording(generatedVideoDTO)
                .summary(generatedSummaryDTO)
                .build();

        return ResponseEntity.ok(stopRecordingResponse);


    }


    private void sendStatusToStartRecording(RecordingDTO recordingDTO) {
        try {
        // send the context and the
        ResponseEntity<?> response = restTemplate.postForEntity(urlServiceLocator.getRECORDING_STARTER_URL(), recordingDTO, String.class);

        }catch (Exception e) {
            log.error("Error while sending signal to model to allow  recording ");
            throw new RuntimeException("Error while sending signal to model to allow  recording");


        }
    }
}
