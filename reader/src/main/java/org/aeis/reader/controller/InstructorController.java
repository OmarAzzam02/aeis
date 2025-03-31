package org.aeis.reader.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.dto.halldto.HallConnectDTO;
import org.aeis.reader.service.instructor.InstructorRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

        @Autowired
        private InstructorRequestHandler instructorRequestHandler;

    @PostMapping("/upload-context")
    public ResponseEntity<?> uploadContext() {
        return null;
    }


    @PostMapping("/upload-recording")
    public ResponseEntity<?> uploadRecording() {
        return null;
    }


    @PostMapping("/upload-summaries")
    public ResponseEntity<?> uploadSummaries() {
        return null;
    }



    @PostMapping("/lecture/recording")
    public ResponseEntity<?> recordLecture(@RequestBody HallConnectDTO hallInfo, HttpServletRequest request) {
        return instructorRequestHandler.isInstructorEligibleToStartRecording(hallInfo , request.getHeader("Authorization").substring(7));
    }






}
