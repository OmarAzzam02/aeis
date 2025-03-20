package org.aeis.usermanagement.dao;

import org.aeis.usermanagement.dto.CourseDto;

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



    @Query("SELECT NEW org.aeis.usermanagement.dto.CourseDto( " +
            "c.id, c.name, c.section, " +
            "NEW org.aeis.usermanagement.dto.CourseTimePeriodDto(cp.days, cp.startTime, cp.endTime)) " +
            "FROM Student s " +
            "JOIN s.registeredCourses c " +
            "JOIN c.courseTimePeriod cp " +
            "WHERE s.id = :userId AND s.role = 'STUDENT'")
    Set<CourseDto> findCoursesByUserId(@Param("userId") Long userId);



    @Query("SELECT NEW org.aeis.usermanagement.dto.CourseDto( " +
            "c.id, c.name, c.section, " +
            "NEW org.aeis.usermanagement.dto.CourseTimePeriodDto(cp.days, cp.startTime, cp.endTime)) " +  // Pass CourseTimePeriodDto
            "FROM Instructor i " +
            "JOIN i.assignedCourses c " +
            "JOIN c.courseTimePeriod cp " +
            "WHERE i.id = :userId")
    Set<CourseDto> findInstructorAssignedCoursesByUserId(@Param("userId") Long userId);




}
