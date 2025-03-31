package org.aeis.reader.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.dto.usersettingsdto.UserSettingDTO;
import org.aeis.reader.service.student.StudentRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRequestHandler studentRequestHandler;

    @PostMapping("/lecture/connect")
    public ResponseEntity<?> connectToLecture(@RequestBody UserSettingDTO userSettingDTO , HttpServletRequest request) {
        return studentRequestHandler.connectToLecture(userSettingDTO, request.getHeader("Authorization").substring(7));
    }



}
