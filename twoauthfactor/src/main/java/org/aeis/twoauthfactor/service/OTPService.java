package org.aeis.twoauthfactor.service;

import org.aeis.twoauthfactor.dto.OtpRequest;
import org.aeis.twoauthfactor.dto.VerifyOtpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class OTPService {

    private Logger log = Logger.getLogger(OTPService.class.getName());

    @Autowired
    private  JavaMailSender emailSender;

    @Autowired
    private OtpCache otpCache;

    private static final int OTP_LENGTH = 4;

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }


    public ResponseEntity<?> sendOtpEmail(OtpRequest request) {

        try {
            String otp = generateOtp();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getEmail());
            message.setSubject("Login Attempt on Your AEIS Account");

            message.setText("Dear"+ request.getFirstName() +",\n" +
                    "\n" +
                    "We detected a login attempt to your account on the AEIS System. For your security, please verify this request by entering the One-Time Password (OTP) below in the application to complete the login process.\n" +
                    "\n" +
                    "Your OTP:"+otp +"\n" +
                    "\n" +
                    "If you did not initiate this request, please disregard this email. Your account remains secure.\n" +
                    "\n" +
                    "If you have any questions or concerns, feel free to contact us at support@aeis.com.\n" +
                    "\n" +
                    "Thank you for using AEIS!\n" +
                    "\n" +
                    "Best regards,\n" +
                    "The AEIS Support Team");


            emailSender.send(message);
            addToCache(request.getEmail(), otp);


            return ResponseEntity.ok().body("OTP sent successfully");
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to send OTP", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Async
    protected void addToCache(String email, String otp) {
        otpCache.addOtpToCache(email,otp);
    }

    public ResponseEntity<?> verifyOtp(VerifyOtpRequest request) {
        String otp = otpCache.getOtpFromCache(request.getEmail());
        if (otp == null) {
            return new ResponseEntity<>("OTP Null", HttpStatus.BAD_REQUEST);
        }
        if (otp.equals(request.getOtp())) {
            otpCache.removeOtpFromCache(request.getEmail());
            return ResponseEntity.ok().body("OTP verified successfully");
        } else {
            return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
        }
    }
}
