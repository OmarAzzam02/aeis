package org.aeis.summary.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class SummaryRequestDTO {
        @JsonProperty("course_ids")
        List<Long> courseIds;
}
