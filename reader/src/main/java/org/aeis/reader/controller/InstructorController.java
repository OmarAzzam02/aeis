package org.aeis.reader.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.service.instructor.InstructorRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

        @Autowired
        private InstructorRequestHandler instructorRequestHandler;




    @PostMapping("/upload-recording")
    public ResponseEntity<?> uploadRecording() {
        return null;
    }


    @PostMapping("/upload-summaries")
    public ResponseEntity<?> uploadSummaries() {
        return null;
    }



    @PostMapping( value = "/start/recording/{hallName}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> recordLecture(
            @PathVariable("hallName") String hallName,
             @RequestPart(value = "pdfFile", required = false) MultipartFile pdfFile
            , HttpServletRequest request) {
        return instructorRequestHandler.isInstructorEligibleToStartRecording(hallName,pdfFile, request.getHeader("Authorization").substring(7));
    }






}
