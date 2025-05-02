package org.aeis.devicelogs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class DeviceDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("hall")
    private String hall;

    @JsonProperty("status")
    private String status;

    @JsonProperty("recording")
    private boolean isRecording;


}
