package org.aeis.aiabstractionlayer.Payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TtsAudioPayload {
    @JsonProperty("frame_id")
    private int frameId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("audio_base64")
    private String audioBase64;
    @JsonProperty("timestamp")
    private double timestamp;
}
