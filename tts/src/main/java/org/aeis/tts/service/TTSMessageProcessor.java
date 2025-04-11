package org.aeis.tts.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class TTSMessageProcessor {
    Logger log = org.slf4j.LoggerFactory.getLogger(TTSMessageProcessor.class);


    @Autowired
    private WebSocketSender webSocketSender;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void SttOutputListener(String message) {
        log.info("Received message: {}", message);
        webSocketSender.send(message);
    }

}
