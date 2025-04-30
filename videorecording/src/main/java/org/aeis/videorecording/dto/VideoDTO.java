package org.aeis.videorecording.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VideoDTO {

    @JsonProperty("courseId")
    private Long courseId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private byte[] content;

}
