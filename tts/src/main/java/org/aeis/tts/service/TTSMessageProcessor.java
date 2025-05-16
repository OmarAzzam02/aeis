package org.aeis.tts.service;

import org.aeis.tts.dto.AudioRequestDTO;
import org.aeis.tts.dto.AudioResponseDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.Queue;


@Service
public class TTSMessageProcessor {
    Logger log = org.slf4j.LoggerFactory.getLogger(TTSMessageProcessor.class);


    @Autowired
    private WebSocketSender webSocketSender;

    ResponseEntity<?> lastResponse = null;
    @Autowired
    Queue <AudioRequestDTO> queue;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}"
            , groupId = "${spring.kafka.consumer.group-id}"
            , containerFactory = "audioDTOConcurrentKafkaListenerContainerFactory")
    public void ttsOutPut(AudioRequestDTO message) {

        queue.add(message);
    }


    public ResponseEntity<?> getAudio() throws IOException {
        if (queue.isEmpty() && lastResponse == null) {
            return ResponseEntity.ok("no audio yet");
        }
        if (queue.isEmpty() && lastResponse != null) {
            return lastResponse;
        }
        AudioRequestDTO audioDTO  = queue.poll();

        byte[] audioBytes = Base64.getDecoder().decode(audioDTO.getAudioFile());

        File wavFile =  getFile(audioBytes);

       MockMultipartFile mock  =  new  MockMultipartFile(
                "audio", wavFile.getName(), "audio/wav", new FileInputStream(wavFile)
         );

       String description = audioDTO.getDescription();

        byte[] bytes= mock.getBytes();

        lastResponse =  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"tts.wav\"")
                .header("X-Description", description)
                .contentType(MediaType.parseMediaType("audio/wav"))
                .body(bytes);



        return lastResponse;
    }

    private File getFile(byte[] audioBytes) throws IOException {



        File wavFile = File.createTempFile("audio_", ".wav");


        try (FileOutputStream fos = new FileOutputStream(wavFile)) {
            fos.write(audioBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("Error writing audio file", e);
        }

        return wavFile;
    }


}
