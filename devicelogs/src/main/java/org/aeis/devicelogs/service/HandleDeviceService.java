package org.aeis.devicelogs.service;


import lombok.Data;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.aeis.devicelogs.dao.DeviceDAO;
import org.aeis.devicelogs.dao.DeviceStatusDAO;
import org.aeis.devicelogs.dto.AddDeviceDTO;
import org.aeis.devicelogs.dto.DeviceDTO;
import org.aeis.devicelogs.dto.DeviceStatusResponseDTO;
import org.aeis.devicelogs.entitiy.Device;
import org.aeis.devicelogs.entitiy.DeviceStatus;
import org.aeis.devicelogs.mapper.DeviceMapperService;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
@Log4j2
public class HandleDeviceService {


    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private DeviceStatusDAO deviceStatusDAO;

    @Autowired
    private DeviceMapperService mapperService;




    public ResponseEntity<?> addDevice(AddDeviceDTO deviceDTO) {
        try {
            Device device = Device.builder()
                    .id(deviceDTO.getId())
                    .hall(deviceDTO.getHall())
                    .build();

            deviceDAO.save(device);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error adding device");
        }
        return ResponseEntity.ok().body("Device added successfully");
    }





    public ResponseEntity<?> addLog(DeviceDTO deviceDTO) {

        try {

            Device device = deviceDAO.findById(deviceDTO.getId()).orElse(null);
            DeviceStatus deviceStatus = DeviceStatus.builder()
                    .status(deviceDTO.getStatus())
                    .isRecording(deviceDTO.isRecording() ? 1 : 0)
                    .timeStamp(addTimeStamp())
                    .device(device)
                    .build();

            deviceStatusDAO.save(deviceStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error adding log");
        }
        return ResponseEntity.ok().body("Log added successfully");
    }


    public ResponseEntity<?> getDeviceStatus(){
        log.info("Fetching device status");
        List<DeviceStatusResponseDTO> allDevices = null;
        try {
            List<Device> devices =  deviceDAO.findAllWithStatuses();
            if (devices.isEmpty()) {
                return ResponseEntity.badRequest().body("No devices found");
            }
             allDevices =  mapperService.mapToResponse(devices);

            if (allDevices.isEmpty() || allDevices == null)
                return ResponseEntity.badRequest().body("No devices found");
        }
        catch (Exception e) {
          return ResponseEntity.internalServerError().body("Error fetching device status");
        }
        return ResponseEntity.ok().body(allDevices);
    }





    private Date addTimeStamp() {
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0);
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(localDateTime.atZone(zone).toInstant());

    }


}
