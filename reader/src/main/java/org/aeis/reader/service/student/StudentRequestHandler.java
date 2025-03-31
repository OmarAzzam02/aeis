package org.aeis.reader.service.student;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.dto.usersettingsdto.UserSettingDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudentRequestHandler {


    public ResponseEntity<?> connectToLecture(UserSettingDTO userSettingDTO , String token) {
        return null;
    }
}
