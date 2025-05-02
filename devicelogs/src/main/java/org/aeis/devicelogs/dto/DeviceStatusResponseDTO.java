package org.aeis.devicelogs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceStatusResponseDTO {

    @JsonProperty("device_info")
    private DeviceInfo device;
    @JsonProperty("status_info")
    private List<StatusInfo> status;
}
