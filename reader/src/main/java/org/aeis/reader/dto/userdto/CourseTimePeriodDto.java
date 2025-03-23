package org.aeis.reader.dto.userdto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalTime;

@AllArgsConstructor
@Builder
public class CourseTimePeriodDto {


    @JsonProperty("course_days")
    private String days;

    @JsonProperty("start_time")
    private LocalTime startTime;
    @JsonProperty("end_time")
    private LocalTime endTime;


}
