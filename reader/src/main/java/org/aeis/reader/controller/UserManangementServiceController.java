package org.aeis.reader.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.aeis.reader.dto.otpdto.ReaderVerifyOtpRequest;
import org.aeis.reader.dto.userdto.LoginResponse;
import org.aeis.reader.dto.userdto.UserLoginRequest;
import org.aeis.reader.service.user.UserManagementRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("user-management-service/")
public class UserManangementServiceController {

    @Autowired
    private UserManagementRequestHandler userManagementRequestHandler;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest loginDto) {
        return userManagementRequestHandler.redirectLoginRequest(loginDto);
    }

    @GetMapping("/users/info/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId, HttpServletRequest request) {
       String token =  request.getHeader("Authorization");
        return userManagementRequestHandler.redirectUserInfoRequest(userId, token);
    }


    @PostMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(HttpServletRequest request) {
        String token =  request.getHeader("Authorization").substring(7);
       return  userManagementRequestHandler.redirectOtpGenerationRequest(token);
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody ReaderVerifyOtpRequest otpRequest , HttpServletRequest request) {
        return userManagementRequestHandler.redirectOtpVerificationRequest(otpRequest,request.getHeader("Authorization").substring(7));
    }






}
