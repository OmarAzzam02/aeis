package org.aeis.aiabstractionlayer.controller;

import org.aeis.aiabstractionlayer.dto.RecordingDTO;
import org.aeis.aiabstractionlayer.service.handler.AiLayerRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AbstractionController {

    @Autowired
    private AiLayerRequestHandler aiLayerRequestHandler;

    @PostMapping("/start-recording")
    public ResponseEntity<?> startRecording(@RequestBody RecordingDTO recordingDTO) {

        return aiLayerRequestHandler.handleStartRecording(recordingDTO);

    }

    @PostMapping("/stop-recording/{hallId}")
    public ResponseEntity<?> stopRecording(@PathVariable String hallId) {
      return aiLayerRequestHandler.handleStopRecording(hallId);
    }


        @PostMapping("/lecture-summary-video")
    public ResponseEntity<?> receiveSummaryAndVideo(
            @RequestParam(value = "courseId") Long courseId,
            @RequestPart(value = "summaryFile")MultipartFile summaryFile,
            @RequestPart(value = "videoFile")MultipartFile videoFile
            ) throws IOException {

         return aiLayerRequestHandler.handleSummaryAndVideo(courseId,summaryFile,videoFile.getBytes());
    }









}
