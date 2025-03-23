package org.aeis.twoauthfactor.controller;


import org.aeis.twoauthfactor.dto.OtpRequest;
import org.aeis.twoauthfactor.dto.VerifyOtpRequest;
import org.aeis.twoauthfactor.service.OTPService;
import org.aeis.twoauthfactor.service.OtpRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp-auth/")
public class TwoAuthFactorController {

    @Autowired
    private OtpRequestHandler otpHandler;

    @PostMapping("/generate")
    public ResponseEntity<?> generateOtp( @RequestBody OtpRequest request) {
       return otpHandler.handleOtpGeneration(request);
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return otpHandler.handleOtpVerification(request);
    }


}
