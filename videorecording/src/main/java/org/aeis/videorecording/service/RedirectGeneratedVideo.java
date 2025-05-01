package org.aeis.videorecording.service;


import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.aeis.videorecording.cache.VideoRecordingCache;
import org.aeis.videorecording.dto.GeneratedVideoRecording;
import org.aeis.videorecording.entity.VideoRecording;
import org.aeis.videorecording.mapper.VideoRecordingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class RedirectGeneratedVideo {

    @Value("${reader.service.url}")
    private String readerUrl;

    @Autowired
    private VideoRecordingCache cache;

    @Autowired
    private VideoRecordingMapper mapperService;

    @Autowired
    private RestTemplate restTemplate;



     public void redirectToReader() {
         log.info("Redirecting Recording to reader service");
         VideoRecording video = cache.getLastAddedSummary();


         if (video == null) {
             log.error("No Video found in cache");
             return;
         }


         GeneratedVideoRecording generatedSummaryDTO = mapperService.mapToGeneratedDTO(video);
         restTemplate.postForEntity(readerUrl, generatedSummaryDTO, Void.class);
     }



}
