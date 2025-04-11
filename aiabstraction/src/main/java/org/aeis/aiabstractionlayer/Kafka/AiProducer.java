package org.aeis.aiabstractionlayer.Kafka;

import org.aeis.aiabstractionlayer.Payload.LectureSummaryRequestPayload;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AiProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public AiProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRecordControl(String action) {
        // "start_recording" or "stop_recording"
        Map<String, String> payload = new HashMap<>();
        payload.put("action", action);
        kafkaTemplate.send("record_control", payload);
    }

    public void sendLectureSummaryRequest(LectureSummaryRequestPayload request) {
        kafkaTemplate.send("lecture_summary_requests", request);
    }
}
