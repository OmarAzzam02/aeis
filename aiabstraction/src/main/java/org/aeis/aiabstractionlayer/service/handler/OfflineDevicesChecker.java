package org.aeis.aiabstractionlayer.service.handler;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Getter
@Component
@Setter
public class OfflineDevicesChecker {

    private volatile Instant lastMessageReceivedTime;
}
