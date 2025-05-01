package org.aeis.videorecording.controller;


import org.aeis.videorecording.dto.VideoDTO;
import org.aeis.videorecording.dto.VideoRecordingRequest;
import org.aeis.videorecording.service.HandleVideoRecordingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VideoRecordingController {


    @Autowired
    private HandleVideoRecordingService handleVideoRecordingService;



    @PostMapping("/save-video-recording")
    public ResponseEntity<?> saveVideoRecording(@RequestBody VideoDTO videoDTO) {
        handleVideoRecordingService.saveVideoRecording(videoDTO);
        return ResponseEntity.ok("Video recording saved successfully");
    }

    @PutMapping("/share-video/{videoId}")
    public ResponseEntity<?> shareVideo(@PathVariable Long videoId) {
        try {
        handleVideoRecordingService.shareVideo(videoId);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error sharing video: " + e.getMessage());
        }
        return ResponseEntity.ok("Video shared successfully");
    }

    @PostMapping("/retrieve-videos")
    public ResponseEntity<?> retrieveVideos(@RequestBody VideoRecordingRequest videoRequest) {
        ResponseEntity<List<VideoDTO>> responseEntity = handleVideoRecordingService.getAllSharedVideos(videoRequest);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<VideoDTO> videoDTOs = responseEntity.getBody();
            if (videoDTOs != null && !videoDTOs.isEmpty()) {
                return ResponseEntity.ok(videoDTOs);
            } else {
                return ResponseEntity.status(404).body("No videos found");
            }
        }
        return ResponseEntity.internalServerError().body("Error retrieving videos");
    }


}
