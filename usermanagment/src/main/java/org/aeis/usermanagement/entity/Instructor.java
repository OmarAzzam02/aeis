package org.aeis.usermanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Instructor {
    private List<Course> assignedCourses;
}
