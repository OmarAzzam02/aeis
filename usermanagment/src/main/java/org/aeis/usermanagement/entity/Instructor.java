    package org.aeis.usermanagement.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.NoArgsConstructor;



    import java.util.Set;


    @NoArgsConstructor
    @AllArgsConstructor
    @DiscriminatorValue("INSTRUCTOR")
    @Entity
    public class Instructor extends User {

        @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
        private Set<Course> assignedCourses;
    }
