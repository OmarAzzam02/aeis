package org.aeis.devicelogs.controller;


import org.aeis.devicelogs.dto.AddDeviceDTO;
import org.aeis.devicelogs.dto.DeviceDTO;
import org.aeis.devicelogs.service.HandleDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceLogsController {


    @Autowired
    private HandleDeviceService handleDeviceService;




    @PostMapping("/add-log")
    public ResponseEntity<?> addLog(@RequestBody DeviceDTO deviceDTO) {
         return handleDeviceService.addLog(deviceDTO);
    }


    @GetMapping("/get-status")
    public ResponseEntity<?> getDeviceStatus() {
        return handleDeviceService.getDeviceStatus();
    }


    @PostMapping("/add-device")
    public ResponseEntity<?> addDevice(@RequestBody AddDeviceDTO addDeviceDTO) {
        return handleDeviceService.addDevice(addDeviceDTO);
    }



}
