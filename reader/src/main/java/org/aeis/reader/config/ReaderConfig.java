package org.aeis.reader.config;


import org.aeis.reader.dto.TokenInfoDto;
import org.aeis.reader.dto.UserSessionDto;
import org.aeis.reader.dto.userdto.UserDTO;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ReaderConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public ConcurrentHashMap<TokenInfoDto, UserDTO> concurrentHashMap() {
        return new ConcurrentHashMap<>();
    }
     @Bean
    public ConcurrentHashMap<String, TokenInfoDto> StringToken() {
        return new ConcurrentHashMap<>();
    }


}
