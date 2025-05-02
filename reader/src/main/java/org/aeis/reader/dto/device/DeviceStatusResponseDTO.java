package org.aeis.reader.dto.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
