package org.aeis.usermanagement.config;


import org.aeis.usermanagement.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class AppConfig {


    @Bean
    public ConcurrentHashMap<Long, UserDTO> concurrentHashMap(){
        return new ConcurrentHashMap<>();
    }


}
