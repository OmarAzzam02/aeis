package org.aeis.aiabstractionlayer.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;


@EnableScheduling
@Configuration
public class AppConfig {



    @Bean
    public ConcurrentHashMap <Long, Instant> deviceStatusMap() {
        return new ConcurrentHashMap<>();
    }



    @LoadBalanced
    @Bean
     public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
