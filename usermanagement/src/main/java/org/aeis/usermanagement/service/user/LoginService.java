package org.aeis.usermanagement.service.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.aeis.usermanagement.dao.UserDao;
import org.aeis.usermanagement.dto.UserLoginDto;
import org.aeis.usermanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@Builder
@AllArgsConstructor
public class LoginService {


    @Autowired
   private  UserDao db;

    @Autowired
    private AuthenticationManager authenticationManager;


    public Optional<User> login(UserLoginDto loginDto) {
            if ( AuthenticateUser(loginDto).isAuthenticated())
                return getAuthUser(loginDto.getEmail());

     return Optional.empty();
    }

    private Authentication AuthenticateUser(UserLoginDto loginDto) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
    }

    private Optional<User> getAuthUser(String email) {
        return db.findByEmail(email);
    }


}
