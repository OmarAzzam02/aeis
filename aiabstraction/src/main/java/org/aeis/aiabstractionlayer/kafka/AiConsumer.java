package org.aeis.aiabstractionlayer.kafka;

import lombok.extern.slf4j.Slf4j;
import org.aeis.aiabstractionlayer.cache.DeviceStatusTrackerCache;
import org.aeis.aiabstractionlayer.dto.DeviceStatusDTO;
import org.aeis.aiabstractionlayer.payload.*;
import org.aeis.aiabstractionlayer.service.handler.AiLayerRequestHandler;
import org.aeis.aiabstractionlayer.service.handler.OfflineDevicesChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class AiConsumer {

    @Autowired
    private AiLayerRequestHandler aiLayerRequestHandler;

    @Autowired
    private DeviceStatusTrackerCache deviceStatusTrackerCache;

    @Autowired
    private OfflineDevicesChecker offlineDevicesChecker;
    @KafkaListener(
            topics = "device_status"
            ,containerFactory = "deviceStatusDTOConcurrentKafkaListenerContainerFactory"
    )
    public void onDeviceStatus(DeviceStatusDTO payload) {
        log.info("[AiConsumer] Device status arrived. id={}, hall={}, status={}, recording={}",
                payload.getId(), payload.getHall(), payload.getStatus(), payload.isRecording());

       deviceStatusTrackerCache.addDeviceStatus(payload.getId(), Instant.now());
       aiLayerRequestHandler.updateDeviceStatus(payload);
    }

    @KafkaListener(
            topics = "stt_translations",
            containerFactory = "sttTranslationsContainerFactory"
    )
    public void onSttTranslationReceived(SttTranslationsPayload payload) {
        log.info("[AiConsumer] STT arrived. original='{}', translated='{}'",
                payload.getOriginalText(), payload.getTranslatedText());
        // Possibly store the transcript in your DB, etc.
    }

    @KafkaListener(
            topics = "lecture_summary_responses",
            containerFactory = "lectureSummariesContainerFactory"
    )
    public void onLectureSummaryResponse(LectureSummaryResponsePayload payload) {
        log.info("[AiConsumer] Summary done for reqId={}, summary='{}'",
                payload.getRequestId(), payload.getSummary());
        // If you have in-flight requests, you can match by requestId
        // and store or pass the result to your application logic.
    }
}
