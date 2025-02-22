package org.aeis.usermanagement.service.user;


import org.aeis.usermanagement.dao.UserDao;
import org.aeis.usermanagement.dto.CourseDto;
import org.aeis.usermanagement.dto.UserInstructorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InstructorInfoService {

    @Autowired
    private UserDao db;


}
