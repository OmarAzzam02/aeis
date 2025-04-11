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
public class LectureSummaryResponsePayload {
    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("timestamp")
    private double timestamp;
}
