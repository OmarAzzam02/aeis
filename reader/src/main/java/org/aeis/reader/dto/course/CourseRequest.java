package org.aeis.reader.dto.course;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Getter
@Setter
public class CourseRequest {


    @JsonProperty("course_ids")
    private List<Long> courseIds;


}
