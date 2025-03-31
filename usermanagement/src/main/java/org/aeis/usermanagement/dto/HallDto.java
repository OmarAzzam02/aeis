package org.aeis.usermanagement.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HallDto {

    @JsonProperty("hall_id")
    private Long id;

    @JsonProperty("hall_name")
    private String name;
}
