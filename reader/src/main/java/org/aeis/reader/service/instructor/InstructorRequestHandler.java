package org.aeis.reader.service.instructor;

import org.aeis.reader.cache.UserSessionCache;
import org.aeis.reader.dto.halldto.HallConnectDTO;
import org.aeis.reader.dto.userdto.CourseDto;
import org.aeis.reader.dto.userdto.Role;
import org.aeis.reader.dto.userdto.UserDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class InstructorRequestHandler {



    private final static String RECORDING_STARTER_URL = "";
    Logger log = org.slf4j.LoggerFactory.getLogger(InstructorRequestHandler.class);

    private Long hallToRecord;

    @Autowired
    private UserSessionCache cache;


    @Autowired
    private RestTemplate restTemplate;






    public ResponseEntity<?> isInstructorEligibleToStartRecording(HallConnectDTO hallConnectDTO, String token) {
        if (!cache.isValidUser(token))
            return ResponseEntity.badRequest().body("User Invalid Session found");

        UserDTO user = cache.getUserFromToken(token);

        if (isAuthorizedToStartRecording(hallConnectDTO,user)){
             sendStatusToStartRecording();
             return ResponseEntity.ok("Recording Started");
        }

        return ResponseEntity.badRequest().body("You have no lecture to start recording");
    }



    private boolean isAuthorizedToStartRecording(HallConnectDTO hallConnectDTO,UserDTO user) {
       return user.getRole().equals(Role.INSTRUCTOR)
              && userHasALecture(hallConnectDTO, user);
    }

    public boolean userHasALecture(HallConnectDTO hallConnectDTO , UserDTO user) {

        String today = LocalDate.now()
                .getDayOfWeek()
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .toLowerCase();


     
        LocalTime now = LocalTime.now().withNano(0);
            for (CourseDto course : user.getCourses()) {

            String days = course.getCourseTimePeriod().getDays().toLowerCase();
            List<String> daysList = List.of(days.split(","));

       
            if (daysList.contains(today)) {
                LocalTime startTime = course.getCourseTimePeriod().getStartTime();
                LocalTime endTime =  course.getCourseTimePeriod().getEndTime();
                
                if (!now.isBefore(startTime)
                        && !now.isAfter(endTime)
                        && course.getHall().getName().
                        equals(hallConnectDTO.getHallName().trim()))
                {
                    this.hallToRecord = course.getHall().getId();
                    return true; 
                }
            }
        }
        return false; 
    }


    @Async
    protected void sendStatusToStartRecording() {
        try {
        ResponseEntity<Void> response = restTemplate.postForEntity(RECORDING_STARTER_URL, hallToRecord, Void.class);

        }catch (Exception e) {
            log.error("Error while sending signal to model to allow  recording ");
            e.printStackTrace();
        }
    }





}
