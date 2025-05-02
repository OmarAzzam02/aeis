package org.aeis.reader.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.reader.service.admin.HandleAdminRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private HandleAdminRequests handleAdminRequests;


    @GetMapping("/device-status")
    public ResponseEntity<?> getDeviceStatus(HttpServletRequest request) {

        return handleAdminRequests.getDeviceStatus(request.getHeader("Authorization").substring(7));

    }


}
