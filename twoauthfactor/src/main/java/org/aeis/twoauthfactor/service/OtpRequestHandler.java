package org.aeis.twoauthfactor.service;


import lombok.extern.log4j.Log4j2;
import org.aeis.twoauthfactor.dto.OtpRequest;
import org.aeis.twoauthfactor.dto.VerifyOtpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class OtpRequestHandler {

    @Autowired
    private OTPService otpService;


    @Autowired
    private OtpCache otpCache;





    public ResponseEntity<?> handleOtpGeneration(OtpRequest request) {
        return otpService.sendOtpEmail(request);

    }


    public ResponseEntity<?> handleOtpVerification(VerifyOtpRequest request) {
        return otpService.verifyOtp(request);

    }
}


