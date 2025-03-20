package org.aeis.usermanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
