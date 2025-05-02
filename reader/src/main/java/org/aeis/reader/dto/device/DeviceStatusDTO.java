package org.aeis.reader.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Builder
@Getter
@Setter
public class DeviceStatusDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("hall")
    private String hall;

    @JsonProperty("recording")
    private int isRecording;

    @JsonProperty("status_time")
    private Date timeStamp;


}
