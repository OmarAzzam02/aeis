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
public class CameraFramePayload {
    @JsonProperty("frame_id")
    private int frameId;
    @JsonProperty("image_base64")
    private String imageBase64;
    @JsonProperty("timestamp")
    private double timestamp;
}
