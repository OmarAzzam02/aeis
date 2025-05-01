package org.aeis.reader.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.cache.summary.GeneratedSummaryCache;
import org.aeis.reader.cache.video.GeneratedVideoCache;
import org.aeis.reader.dto.summary.GeneratedSummaryDTO;
import org.aeis.reader.dto.video.GeneratedVideoDTO;
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

    @Autowired
    private GeneratedSummaryCache summaryCache;

    @Autowired
    private GeneratedVideoCache videoCache;


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
        return instructorRequestHandler.startRecording(hallName.trim(),pdfFile, request.getHeader("Authorization").substring(7));
    }




    @PostMapping("/stop/recording/{hallName}")
    public ResponseEntity<?> stopRecording(
            @PathVariable("hallName") String hallName,
            HttpServletRequest request) {
        return instructorRequestHandler.stopRecording(hallName.trim(),request.getHeader("Authorization").substring(7));
    }



    @PutMapping("/share-summary/{id}")
    public ResponseEntity<?> shareSummary(@PathVariable Long id){
        try {
        instructorRequestHandler.shareSummary(id);
        return ResponseEntity.ok("Summary shared successfully");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error sharing summary: " + e.getMessage());
        }


    }


    @PutMapping("/share-video/{videoId}")
    public ResponseEntity<?> shareVideo(@PathVariable Long videoId) {
        try {
            instructorRequestHandler.shareVideo(videoId);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error sharing video: " + e.getMessage());
        }
        return ResponseEntity.ok("Video shared successfully");
    }






    @PostMapping("/view-generated-summary")
    public void  viewGeneratedSummary(@RequestBody GeneratedSummaryDTO summaryDTO) {
        summaryCache.addGeneratedSummary(summaryDTO);
    }

    @PostMapping("/view-generated-video")
    public void   viewGeneratedVideo(@RequestBody GeneratedVideoDTO videoDTO) {
        videoCache.addVideoRecording(videoDTO);
    }



}
