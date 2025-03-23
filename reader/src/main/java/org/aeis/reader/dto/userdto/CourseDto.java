package org.aeis.reader.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CourseDto {

    @JsonProperty("course_id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("section")
    private int section;

    @JsonProperty("course_time_period")
    private CourseTimePeriodDto courseTimePeriod;
}
