package org.aeis.usermanagement.service.user;


import lombok.extern.log4j.Log4j2;
import org.aeis.usermanagement.dao.UserDao;
import org.aeis.usermanagement.dto.UserLoginDto;
import org.aeis.usermanagement.entity.User;
import org.aeis.usermanagement.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class LoginService {


    @Autowired
   private UserDao db;

    public LoginResponse login(UserLoginDto loginDto) {

        try {

         log.info("Attempting to login");
         Optional<User>  attemptedUser=  db.findByEmail(loginDto.getEmail());


            if(!attemptedUser.isPresent())
                return  new LoginResponse(false, null, "User not found");

            User user = attemptedUser.get();

            if(user.getPassword().equals(loginDto.getPassword())) {
                log.info("User logged in successfully");
                return new LoginResponse(true, "token", "User logged in successfully");
            }

            else {
                log.error("User login failed");
                return new LoginResponse(false, null, "User login failed Invalid Credentials");
            }

        }catch (Exception e){
            log.error("Error while logging in {} " ,  e.getMessage());
            e.printStackTrace();
            return new LoginResponse(false, null, "Error while logging in Exception ");
        }
    }
}
