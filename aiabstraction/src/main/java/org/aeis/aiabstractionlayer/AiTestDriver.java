package org.aeis.aiabstractionlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aeis.aiabstractionlayer.dto.RecordingDTO;
import org.aeis.aiabstractionlayer.service.ai.AiService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

@Component
public class AiTestDriver  implements CommandLineRunner {

    private final AiService aiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiTestDriver(AiService aiService) {
        this.aiService = aiService;
    }

   @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Starting AI Test Driver ===");

        System.out.println("Sending 'start_recording' command...");
        aiService.startRecording(RecordingDTO.builder()
                .courseId(1L)
                .contextFile("dummy_context_file.pdf".getBytes())
                .build());

        Thread.sleep(5000);

        String dummyTranscript = "This is a test lecture transcript that covers advanced topics in AI and machine learning.";
        byte[] dummyContext = "Dummy PDF content as bytes".getBytes();

        System.out.println("Requesting lecture summary for transcript...");
        String requestId = aiService.requestLectureSummary(dummyTranscript, dummyContext);
        System.out.println("Lecture summary request sent with requestId: " + requestId);

        startConsumer("lecture_summary_responses");
        startConsumer("tts_audio");
        startConsumer("stt_translations");

        Thread.sleep(10000);

        System.out.println("Sending 'stop_recording' command...");
      aiService.stopRecording();

        System.out.println("=== AI Test Driver finished ===");
    }

    private void startConsumer(String topic) {
        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "6.tcp.ngrok.io:11601");
                props.put(ConsumerConfig.GROUP_ID_CONFIG, "ai-test-driver-" + topic + "-" + UUID.randomUUID());
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
                consumer.subscribe(Collections.singletonList(topic));

                System.out.println("[Java] Listening on topic: " + topic);
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("\n[Java] Received from " + topic + ":");
                        System.out.println(record.value());
                    }
                }
            } catch (Exception e) {
                System.err.println("[Java] Error in Kafka consumer for topic " + topic + ": " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

}
