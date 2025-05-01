package org.aeis.reader.dto.recording;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aeis.reader.dto.summary.GeneratedSummaryDTO;
import org.aeis.reader.dto.video.GeneratedVideoDTO;



@Builder
@Getter
@Setter
public class StopRecordingResponse {

    @JsonProperty("video_recording")
    private GeneratedVideoDTO recording;

    @JsonProperty("summary")
    private GeneratedSummaryDTO summary;
}
