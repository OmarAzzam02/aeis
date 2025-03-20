package org.aeis.usermanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@DiscriminatorValue("STUDENT")
public class Student extends User {
    @ManyToMany(mappedBy = "registeredStudents")
    Set<Course> registeredCourses;
}
