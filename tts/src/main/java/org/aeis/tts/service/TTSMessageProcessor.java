package org.aeis.tts.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    Queue <String> queue;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void SttOutputListener(String message) {
        queue.add(message);
    }


    public MultipartFile getAudio() throws IOException {
        if (queue.isEmpty()){
            return null;
        }
        String audio  = queue.poll();
        byte[] audioBytes = Base64.getDecoder().decode(audio);

        File wavFile =  getFile(audioBytes);

        return new MockMultipartFile(
                "audio", wavFile.getName(), "audio/wav", new FileInputStream(wavFile)
        );
    }

    private File getFile(byte[] audioBytes) throws IOException {
        File wavFile = File.createTempFile("audio_", ".wav");


        try (FileOutputStream fos = new FileOutputStream(wavFile)) {
            fos.write(audioBytes);
        }
        catch (Exception e) {
            log.error("Error writing audio file", e);
        }

        return wavFile;
    }


}
