package org.aeis.reader.dto.device;


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
