package org.aeis.summary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SummaryDTO {

    @JsonProperty("courseId")
    Long courseId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private byte[] content;

}