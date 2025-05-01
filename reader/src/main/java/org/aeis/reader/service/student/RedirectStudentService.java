package org.aeis.reader.service.student;


import org.aeis.aiabstractionlayer.dto.VideoDTO;
import org.aeis.reader.dto.course.CourseRequest;
import org.aeis.reader.dto.summary.SummaryDTO;
import org.aeis.reader.service.handler.UrlServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
