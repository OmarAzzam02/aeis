package org.aeis.videorecording.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class VideoRecordingRequest {

    @JsonProperty("course_ids")
    List<Long> courseIds;

}
