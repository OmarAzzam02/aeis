package org.aeis.reader.dto.userdto;


import java.util.Set;

public interface UserDTO {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    Role getRole();
    Set<CourseDto> getCourses();
}
