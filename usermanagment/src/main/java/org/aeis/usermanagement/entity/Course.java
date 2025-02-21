package org.aeis.usermanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Course {

    private String id;
    private String name;
    private int section;
    private Instructor teacher;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Hall hall;
}
