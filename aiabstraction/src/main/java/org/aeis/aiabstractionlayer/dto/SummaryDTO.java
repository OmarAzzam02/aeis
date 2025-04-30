package org.aeis.aiabstractionlayer.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SummaryDTO {

    @JsonProperty("courseId")
    Long courseId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private byte[] content;

 }
