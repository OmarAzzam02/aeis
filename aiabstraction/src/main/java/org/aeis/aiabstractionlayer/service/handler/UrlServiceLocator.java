package org.aeis.aiabstractionlayer.service.handler;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class UrlServiceLocator{

    @Value("${summary.service.url}")
    private String summaryServiceUrl;

    @Value("${video.service.url}")
    private String videoServiceUrl;

    @Value("${device.service.url}")
    private String deviceServiceUrl;


}
