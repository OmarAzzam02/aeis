package org.aeis.reader.service.student;


import  org.aeis.reader.dto.video.VideoDTO;
import org.aeis.reader.dto.course.CourseRequest;
import org.aeis.reader.dto.summary.SummaryDTO;
import org.aeis.reader.service.handler.UrlServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RedirectStudentService {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private UrlServiceLocator urlServiceLocator;

    public ResponseEntity<List<SummaryDTO>> redirectToSummaryService(CourseRequest courseRequest) {

        String url = urlServiceLocator.getSUMMARY_RETRIEVE_URL();

        ResponseEntity<List<SummaryDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(courseRequest),
                new ParameterizedTypeReference<List<SummaryDTO>>() {}
        );

        return response;
    }


    public ResponseEntity<List<VideoDTO>> redirectToVideoService(CourseRequest courseRequest) {

        String url = urlServiceLocator.getVIDEO_RETRIEVE_URL();

        ResponseEntity<List<VideoDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(courseRequest),
                new ParameterizedTypeReference<List<VideoDTO>>() {
                }
        );

        return response;


    }

    public ResponseEntity<?> redirectToTTSService() {
        String url = urlServiceLocator.getTTS_URL();

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                null,
                byte[].class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            byte[] audioBytes = response.getBody();

        }

        return ResponseEntity.internalServerError().body("Error retrieving audio file in reader");
    }
}
