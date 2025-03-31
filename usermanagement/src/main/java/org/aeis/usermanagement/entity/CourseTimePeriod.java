package org.aeis.usermanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
@Table(name = "course_time_period")
@NamedQuery(name = "CourseTimePeriod.findAll", query = "SELECT cp FROM CourseTimePeriod cp")

public class CourseTimePeriod {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_time_period_seq")
    @Column(name = "period_id")
    private Long id;

    @Column(name = "days", nullable = false)
    private String days;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

}
