package org.aeis.reader.dto.summary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class GeneratedSummaryDTO {

    @JsonProperty("id")
    Long id;

    @JsonProperty("courseId")
    Long courseId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private byte[] content;
}
