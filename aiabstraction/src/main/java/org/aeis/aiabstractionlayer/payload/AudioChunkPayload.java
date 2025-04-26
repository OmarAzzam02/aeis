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
public class AudioChunkPayload {
    @JsonProperty("timestamp")
    private double timestamp;
    @JsonProperty("audio_base64")
    private String audioBase64;
    @JsonProperty("channels")
    private int channels;
    @JsonProperty("rate")
    private int rate;
    @JsonProperty("format")
    private String format; // e.g. "int16"
}
