package org.aeis.usermanagement.dao;

import org.aeis.usermanagement.dto.CourseDto;

import org.aeis.usermanagement.entity.Course;
import org.aeis.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);



    @Query("SELECT c FROM User u " +
            "JOIN u.registeredCourses c " +
            "JOIN FETCH c.hall " +
            "JOIN FETCH c.courseTimePeriod " +
            "WHERE u.id = :userId AND u.role = 'STUDENT'")
    Set<Course> findCoursesByUserId(@Param("userId") Long userId);


    @Query("SELECT c FROM User u " +
            "JOIN u.assignedCourses c " +
            "JOIN FETCH c.hall " +
            "JOIN FETCH c.courseTimePeriod " +
            "WHERE u.id = :userId AND u.role = 'INSTRUCTOR'")
    Set<Course> findInstructorAssignedCoursesByUserId(@Param("userId") Long userId);




}
