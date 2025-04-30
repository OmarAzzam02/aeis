package org.aeis.aiabstractionlayer.kafka;

import org.aeis.aiabstractionlayer.payload.LectureSummaryRequestPayload;
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

    public void sendRecordControl(String action, byte[] contextFile, Long courseId) {
        // "start_recording"
        Map<String, String> payload = new HashMap<>();
        payload.put("action", action);
        payload.put("context_file", java.util.Base64.getEncoder().encodeToString(contextFile));
        payload.put("course_id", courseId.toString());

        kafkaTemplate.send("record_control", payload);
    }


    public void sendRecordControl(String action) {
        //  "stop_recording"
        Map<String, String> payload = new HashMap<>();
        payload.put("action", action);

        kafkaTemplate.send("record_control", payload);
    }

    public void sendLectureSummaryRequest(LectureSummaryRequestPayload request) {
        kafkaTemplate.send("lecture_summary_requests", request);
    }
}
