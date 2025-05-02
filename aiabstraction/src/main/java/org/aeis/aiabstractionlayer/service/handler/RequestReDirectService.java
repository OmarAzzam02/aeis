package org.aeis.aiabstractionlayer.service.handler;


import lombok.extern.log4j.Log4j2;
import org.aeis.aiabstractionlayer.dto.DeviceStatusDTO;
import org.aeis.aiabstractionlayer.dto.SummaryDTO;
import org.aeis.aiabstractionlayer.dto.VideoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class RequestReDirectService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UrlServiceLocator urlServiceLocator;
    public void sendSummaryToSummaryService(SummaryDTO summaryDTO) {

        try {
        ResponseEntity<String> response =  restTemplate.postForEntity(urlServiceLocator.getSummaryServiceUrl(), summaryDTO, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Summary sent successfully to summary service");
        } else {
            log.error("Failed to send summary to summary service, status code: " + response.getStatusCode());
        }
        }catch (Exception e){
        log.error("Error sending summary to summary service", e);
         throw new RuntimeException("Error sending summary to summary service", e);
        }
    }


    public void sendVideoToRecordingService(VideoDTO videoDTO) {
        try {
        restTemplate.postForEntity(urlServiceLocator.getVideoServiceUrl(), videoDTO, Void.class);
        }catch (Exception e){
            log.error("Error sending video to recording service", e);
            throw new RuntimeException("Error sending video to recording service", e);
        }
    }


    public void sendDeviceStatus(DeviceStatusDTO deviceStatusDTO) {
        try {
            restTemplate.postForEntity(urlServiceLocator.getDeviceServiceUrl(), deviceStatusDTO, Void.class);
            log.info("Device status sent successfully to recording service");
        }catch (Exception e){
            log.error("Error sending device status to recording service", e);
            throw new RuntimeException("Error sending device status to recording service", e);
        }
    }
}
