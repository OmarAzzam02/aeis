package org.aeis.reader.dto.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GeneratedVideoDTO {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("courseId")
    private Long courseId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private byte[] content;

}
