package org.aeis.aiabstractionlayer.service.handler;


import org.aeis.aiabstractionlayer.dto.RecordingDTO;
import org.aeis.aiabstractionlayer.payload.AiCommandPayload;
import org.aeis.aiabstractionlayer.service.ai.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AiLayerRequestHandler {


    @Autowired
    private AiService aiService;



    public ResponseEntity<?> handleStartRecording(RecordingDTO recordingDTO) {
        aiService.startRecording();
        return ResponseEntity.ok("Recording started");
    }


    public ResponseEntity<?> handleStopRecording(String hallId) {
        aiService.stopRecording();
        return ResponseEntity.ok("Recording stopped");
    }











}
