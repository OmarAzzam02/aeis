package org.aeis.summary.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Builder
@Getter
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
