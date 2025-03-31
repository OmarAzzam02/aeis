package org.aeis.reader.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@AllArgsConstructor
@Getter
public class CourseDto {

    @JsonProperty("course_id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("section")
    private int section;


    @JsonProperty("course_time_period")
    private CourseTimePeriodDto courseTimePeriod;


    @JsonProperty("hall")
    private HallDto hall;

    public HallDto getHall() {
        return hall;
    }

    public CourseTimePeriodDto getCourseTimePeriod() {
        return courseTimePeriod;
    }


    public int getSection() {
        return section;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

}
