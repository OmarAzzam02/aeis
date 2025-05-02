package org.aeis.aiabstractionlayer.service.handler;


import lombok.extern.log4j.Log4j2;
import org.aeis.aiabstractionlayer.cache.DeviceStatusTrackerCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Log4j2
@Component
public class HandleOfflineDeviceStatus {

    @Autowired
    private AiLayerRequestHandler aiLayerRequestHandler;

    @Autowired
    private DeviceStatusTrackerCache deviceStatusTrackerCache;

    @Scheduled(fixedDelay = 10000)
    public void trackOfflineDevices() {

        log.info("[HandleOfflineDeviceStatus] Checking for offline devices...");
        deviceStatusTrackerCache.getDeviceStatusMap().forEach((deviceId, lastMessageReceivedTime) -> {
            Duration duration = Duration.between( deviceStatusTrackerCache.getDeviceStatus(deviceId),Instant.now());
            if (duration.toSeconds() > 30) {
                deviceStatusTrackerCache.removeDeviceStatus(deviceId);
                aiLayerRequestHandler.handleOfflineDeviceStatus(deviceId);
            }
        });

    }



}
