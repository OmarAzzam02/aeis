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
public class LectureSummaryRequestPayload {
    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("transcript")
    private String transcript;

    @JsonProperty("contextBase64")
    private String contextBase64; // if you have a PDF or something
}
