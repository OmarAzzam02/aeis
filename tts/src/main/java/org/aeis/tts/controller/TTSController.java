package org.aeis.tts.controller;


import org.aeis.tts.service.TTSMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TTSController {



    @Autowired
    private TTSMessageProcessor ttsMessageProcessor;

    @PostMapping("/audio")
    public ResponseEntity<?> getAudioFile(){
        try {
        MultipartFile  file = ttsMessageProcessor.getAudio();
         byte[] audioBytes= file.getBytes();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"tts.wav\"")
                    .contentType(MediaType.parseMediaType("audio/wav"))
                    .body(audioBytes);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("no audio yet");
        }

    }



}
