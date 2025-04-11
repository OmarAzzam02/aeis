package org.aeis.aiabstractionlayer.Kafka;

import lombok.extern.slf4j.Slf4j;
import org.aeis.aiabstractionlayer.Payload.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AiConsumer {

    @KafkaListener(
            topics = "tts_audio",
            containerFactory = "ttsAudioContainerFactory"
    )
    public void onTtsAudioReceived(TtsAudioPayload payload) {
        log.info("[AiConsumer] TTS Audio arrived. frameId={}, desc={}",
                payload.getFrameId(), payload.getDescription());
        // You could store or route the TTS MP3 somewhere
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
