package org.aeis.reader.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.dto.usersettingsdto.UserSettingDTO;
import org.aeis.reader.service.student.StudentRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRequestHandler studentRequestHandler;

    @PostMapping("/lecture/connect/{hallId}")
    public ResponseEntity<?> connectToLecture(@PathVariable String hallId,
                                              @RequestBody UserSettingDTO userSettingDTO ,
                                              HttpServletRequest request) {

        return studentRequestHandler.connectToLecture(hallId , userSettingDTO, request.getHeader("Authorization").substring(7));
    }



}
