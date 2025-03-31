package org.aeis.usermanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "COURSES")
@NamedQuery(name = "Course.findAll", query = "SELECT c FROM User c")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String name;
    private int section;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "USER_ID")
    private User teacher;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<User> registeredStudents;


    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;


    @ManyToOne
    @JoinColumn(name = "course_time_period_id", referencedColumnName = "period_id")
   private CourseTimePeriod courseTimePeriod;




}
