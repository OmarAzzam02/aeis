package org.aeis.aiabstractionlayer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStatusDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("hall")
    private String hall;

    @JsonProperty("status")
    private String status;

    @JsonProperty("recording")
    private boolean isRecording;

}
