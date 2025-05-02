package org.aeis.aiabstractionlayer.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DeviceStatusTrackerCache {

    @Autowired
    private ConcurrentHashMap<Long, Instant> deviceStatusMap;


    public void addDeviceStatus(Long deviceId, Instant status) {
        deviceStatusMap.put(deviceId, status);
    }

    public Instant getDeviceStatus(Long deviceId) {
        return deviceStatusMap.get(deviceId);
    }
    public void removeDeviceStatus(Long deviceId) {
        deviceStatusMap.remove(deviceId);
    }
    public ConcurrentHashMap<Long, Instant> getDeviceStatusMap() {
        return deviceStatusMap;
    }




}
