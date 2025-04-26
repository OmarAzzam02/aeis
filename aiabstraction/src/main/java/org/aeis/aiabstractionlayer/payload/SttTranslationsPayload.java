package org.aeis.aiabstractionlayer.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SttTranslationsPayload {
    @JsonProperty("original_text")
    private String originalText;
    @JsonProperty("translated_text")
    private String translatedText;
    @JsonProperty("translated_audio")
    private String translatedAudio; // base64-encoded mp3
    @JsonProperty("timestamp")
    private double timestamp;
}
