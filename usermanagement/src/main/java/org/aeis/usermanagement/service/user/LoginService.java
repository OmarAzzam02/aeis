package org.aeis.usermanagement.service.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.aeis.usermanagement.cache.UserInfoCache;
import org.aeis.usermanagement.dao.UserDao;
import org.aeis.usermanagement.dto.UserLoginDto;
import org.aeis.usermanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    private UserInfoCache userInfoCache;

    @Autowired
   private  UserDao db;

    @Autowired
    private AuthenticationManager authenticationManager;


    public Optional<User> login(UserLoginDto loginDto) {
        try {
            if (!AuthenticateUser(loginDto).isAuthenticated())
                return Optional.empty();

            Optional<User> user = getAuthUser(loginDto.getEmail());
            cacheUserInfo(user.get());
            return user;

        }catch (Exception e) {
            log.error("Error while authenticating user: {}", e.getMessage());
            return Optional.empty();
        }



    }

    @Async
    protected void cacheUserInfo(User user) {
        userInfoCache.addUserInfoToCache(user);
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
