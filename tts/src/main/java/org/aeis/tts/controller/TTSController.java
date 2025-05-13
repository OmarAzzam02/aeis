package org.aeis.tts.controller;


import org.aeis.tts.service.TTSMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TTSController {



    @Autowired
    private TTSMessageProcessor ttsMessageProcessor;

    @PostMapping("/audio")
    public ResponseEntity<byte[]> getAudioFile(){
        try {
        MultipartFile  file = ttsMessageProcessor.getAudio();
        return ResponseEntity.ok(file.getBytes());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }



}
