package org.aeis.devicelogs.mapper;


import org.aeis.devicelogs.dto.DeviceDTO;
import org.aeis.devicelogs.dto.DeviceInfo;
import org.aeis.devicelogs.dto.DeviceStatusResponseDTO;
import org.aeis.devicelogs.dto.StatusInfo;
import org.aeis.devicelogs.entitiy.Device;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeviceMapperService {



    public DeviceDTO mapToDTO(DeviceDTO deviceDTO) {
        return DeviceDTO.builder()
                .id(deviceDTO.getId())
                .hall(deviceDTO.getHall())
                .status(deviceDTO.getStatus())
                .isRecording(deviceDTO.isRecording())
                .build();
    }


    public Device mapToEntity(DeviceDTO deviceDTO) {
        return Device.builder()
                .id(deviceDTO.getId())
                .hall(deviceDTO.getHall())
              //  .status(deviceDTO.getStatus())
             //   .isRecording(deviceDTO.isRecording() ? 1 : 0)
                .build();
    }

    public List<DeviceStatusResponseDTO> mapToResponse(List<Device> devices) {
        return devices.stream().map(device -> DeviceStatusResponseDTO.builder()
                        .device(DeviceInfo.builder()
                                .id(device.getId())
                                .hall(device.getHall())
                                .build())
                        .status(device.getStatuses().stream().map(status -> StatusInfo.builder()
                                        .status(status.getStatus())
                                        .isRecording(status.getIsRecording())
                                        .timeStamp(status.getTimeStamp())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

}
