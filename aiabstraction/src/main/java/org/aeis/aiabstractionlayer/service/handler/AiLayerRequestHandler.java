package org.aeis.aiabstractionlayer.service.handler;


import lombok.extern.log4j.Log4j2;
import org.aeis.aiabstractionlayer.dto.DeviceStatusDTO;
import org.aeis.aiabstractionlayer.dto.RecordingDTO;
import org.aeis.aiabstractionlayer.dto.SummaryDTO;
import org.aeis.aiabstractionlayer.dto.VideoDTO;
import org.aeis.aiabstractionlayer.payload.AiCommandPayload;
import org.aeis.aiabstractionlayer.service.ai.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@Service
public class AiLayerRequestHandler {


    @Autowired
    private AiService aiService;


    @Autowired
    private RequestReDirectService requestReDirectService;


    public ResponseEntity<?> handleStartRecording(RecordingDTO recordingDTO) {
        aiService.startRecording(recordingDTO);
        return ResponseEntity.ok("Recording started");
    }


    public ResponseEntity<?> handleStopRecording(String hallId) {
        aiService.stopRecording();
        return ResponseEntity.ok("Recording stopped");
    }


    public ResponseEntity<?> handleSummaryAndVideo(Long courseId, MultipartFile summaryFile , MultipartFile videoFile) {
        try {
            SummaryDTO summaryDTO = buildSummaryDTO(courseId, summaryFile);
            VideoDTO videoDTO = buildVideoDTO(courseId, videoFile);
            requestReDirectService.sendVideoToRecordingService(videoDTO);
            requestReDirectService.sendSummaryToSummaryService(summaryDTO);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
        return ResponseEntity.ok("Files sent successfully");
    }


    public void updateDeviceStatus(DeviceStatusDTO deviceStatusDTO) {
        DeviceStatusDTO newDTO = convertToAppropriateData(deviceStatusDTO);
        requestReDirectService.sendDeviceStatus(newDTO);
    }

    private DeviceStatusDTO convertToAppropriateData(DeviceStatusDTO deviceStatusDTO) {
        if (deviceStatusDTO.getStatus().equalsIgnoreCase("ok")
            && deviceStatusDTO.isRecording())
            deviceStatusDTO.setStatus("Recording");

        else if (deviceStatusDTO.getStatus().equalsIgnoreCase("ok")
                && !deviceStatusDTO.isRecording()){
            deviceStatusDTO.setStatus("Online");
        }


        return deviceStatusDTO;

    }






    private VideoDTO buildVideoDTO(Long courseId, MultipartFile videoFile) throws IOException {
        return VideoDTO.builder()
                .title(videoFile.getOriginalFilename())
                .courseId(courseId)
                .content(videoFile.getBytes())
                .build();
    }

    private SummaryDTO buildSummaryDTO(Long courseId, MultipartFile summaryFile) throws IOException {
        return SummaryDTO.builder()
                .courseId(courseId)
                .title(summaryFile.getOriginalFilename())
                .content(summaryFile.getBytes())
                .build();
    }


    public void handleOfflineDeviceStatus(Long deviceId) {
        log.info("Device is offline, sending status to device service with id: {}", deviceId);
        DeviceStatusDTO deviceStatusDTO = DeviceStatusDTO.builder()
                .id(deviceId)
                .status("Offline")
                .isRecording(false)
                .build();
        requestReDirectService.sendDeviceStatus(deviceStatusDTO);
    }
}

