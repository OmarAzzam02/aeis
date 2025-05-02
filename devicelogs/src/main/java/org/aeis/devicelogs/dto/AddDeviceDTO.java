package org.aeis.devicelogs.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AddDeviceDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("hall")
    private String hall;

}
