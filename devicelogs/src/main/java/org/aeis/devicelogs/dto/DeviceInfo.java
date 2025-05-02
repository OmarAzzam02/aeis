package org.aeis.devicelogs.dto;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfo {
    private Long id;
    private String hall;
}
