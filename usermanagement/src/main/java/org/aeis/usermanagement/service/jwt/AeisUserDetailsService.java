package org.aeis.usermanagement.service.jwt;

import org.aeis.usermanagement.dao.UserDao;
import org.aeis.usermanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AeisUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public AeisUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new AeisUserDetails(user);
    }
}
