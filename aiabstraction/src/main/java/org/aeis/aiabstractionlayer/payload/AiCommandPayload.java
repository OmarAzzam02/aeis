package org.aeis.aiabstractionlayer.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiCommandPayload {

    /**
     * Example commands:
     * "START_RECORDING", "STOP_RECORDING", "GENERATE_SUMMARY"
     */
    @JsonProperty("command")
    private String command;

    /**
     * If the command is "GENERATE_SUMMARY", you might pass:
     *   - sttTranscript
     *   - contextFile (base64-encoded PDF or text)
     */
    @JsonProperty("stt_transcript")
    private String sttTranscript;

    @JsonProperty("context_file_base64")
    private String contextFileBase64;

    /**
     * Possibly any other arguments or parameters.
     */
    @JsonProperty("language")
    private String language;

    @JsonProperty("voice")
    private String voice;
}
