package org.aeis.usermanagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {


    @ManyToMany(mappedBy = "registeredStudents")
    Set<Course> registeredCourses;
}
