package org.aeis.aiabstractionlayer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RecordingDTO {
    @JsonProperty("hall_id")
    private Long hallId;
    @JsonProperty("course_id")
    private Long courseId;
    @JsonProperty("context_file")
    private byte[] contextFile;

    public RecordingDTO(Long hallId, Long courseId, byte[] contextFile) {
        this.hallId = hallId;
        this.courseId = courseId;
        this.contextFile = contextFile;
    }

    public Long getHallId() {
        return hallId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public byte[] getContextFile() {
        return contextFile;
    }
}
