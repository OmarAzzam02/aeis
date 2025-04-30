package org.aeis.videorecording.config;


import org.aeis.videorecording.entity.VideoRecording;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class VideoRecordingConfig {

    @Bean
    public List<VideoRecording> videoRecordings(){
        return new ArrayList<>();
    }



    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
