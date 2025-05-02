package org.aeis.reader.service.admin;


import org.aeis.reader.dto.device.DeviceStatusDTO;
import org.aeis.reader.dto.device.DeviceStatusResponseDTO;
import org.aeis.reader.dto.video.VideoDTO;
import org.aeis.reader.service.handler.UrlServiceLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RedirectAdminService{


    @Autowired
    private UrlServiceLocator urlServiceLocator;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> getDevicesStatus() {

        String url = urlServiceLocator.getDEVICE_STATUS_URL();
        ResponseEntity<?> response = null;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(null),
                    new ParameterizedTypeReference<List<DeviceStatusResponseDTO>>() {
                    }
            );
        } catch (Exception e) {
            response =  ResponseEntity.internalServerError().body(e.getMessage());
        }

        return response;

    }




}
