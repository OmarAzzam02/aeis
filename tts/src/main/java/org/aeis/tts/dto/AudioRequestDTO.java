package org.aeis.tts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioRequestDTO {


    @JsonProperty("frame_id")
    private Long frameId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("audio_base64")
    private String audioFile;
    @JsonProperty("timpestamp")
    private double timestamp;
}

