package org.aeis.reader.service.user;


import lombok.extern.log4j.Log4j2;
import org.aeis.reader.cache.user.TokenCache;
import org.aeis.reader.cache.user.UserSessionCache;
import org.aeis.reader.dto.TokenInfoDto;
import org.aeis.reader.dto.otpdto.ReaderVerifyOtpRequest;
import org.aeis.reader.dto.otpdto.VerifyOtpRequest;
import org.aeis.reader.dto.userdto.LoginResponse;
import org.aeis.reader.dto.otpdto.OtpRequest;
import org.aeis.reader.dto.userdto.UserDTO;
import org.aeis.reader.dto.userdto.UserLoginRequest;
import org.aeis.reader.service.handler.UrlServiceLocator;
import org.aeis.reader.util.ValidateTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
@Log4j2
public class UserManagementRequestHandler {


    @Autowired
    private UrlServiceLocator urlServiceLocator;

    @Autowired
    private UserSessionCache userSessionCache;


    @Autowired
    private TokenCache tokenCache;

    @Autowired
    private ValidateTokenService validateTokenService;
    @Autowired
    private RestTemplate restTemplate;

   public  ResponseEntity<LoginResponse> redirectLoginRequest(UserLoginRequest loginRequest) {
       try {
           ResponseEntity<LoginResponse> response =  restTemplate.postForEntity(urlServiceLocator.getLoginServiceUrl(), loginRequest, LoginResponse.class);
           log.info("Login response received: {}", response.getBody());
           return response;

       }catch (Exception e) {
           log.info("Error while authenticating user: {}");
          LoginResponse response =  LoginResponse.builder()
                   .role("UNAUTHORIZED")
                   .token("")
                   .message("Error while authenticating user")
                   .build();
           return ResponseEntity.badRequest().body(response);
       }

    }


        public ResponseEntity<?> redirectUserInfoRequest(String token) {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+token);
            HttpEntity entity = new HttpEntity<>(headers);
            ResponseEntity<?> response = null;
            try {
                return restTemplate.exchange(
                        urlServiceLocator.getUserInfoServiceUrl(),
                        HttpMethod.GET,
                        entity,
                        Object.class
                );
            } catch (HttpClientErrorException e) {
                log.info("Client error while calling User Management service: {}");
                return ResponseEntity.status(e.getStatusCode()).body("Access Denied: " + e.getMessage());
            } catch (HttpServerErrorException e) {
                log.info("Server error from User Management service: {}");
                return ResponseEntity.status(e.getStatusCode()).body("User Management service error: " + e.getMessage());
            } catch (ResourceAccessException e) {
                log.info("Failed to reach User Management service: {}");
                return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Service Unavailable: " + e.getMessage());
            } catch (Exception e) {
                log.info("Unexpected error while calling User Management service: {}");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
            }

        }

        public ResponseEntity<String> redirectOtpGenerationRequest( String token) {

            try {
               boolean isTokenValid = validateTokenService.checkTokenValidity(token);

               if (!isTokenValid)
                   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");

               OtpRequest otpRequest = buildOtpRequest(token);

                ResponseEntity<String> response =  restTemplate.postForEntity(urlServiceLocator.getGenerateOtpServiceUrl(), otpRequest, String.class);

                return response;
            } catch (HttpClientErrorException e) {
                log.info("Client error while calling User Management service: {}");
                return ResponseEntity.status(e.getStatusCode()).body("Access Denied: " + e.getMessage());
            } catch (HttpServerErrorException e) {
                log.info("Server error from User Management service: {}");
                return ResponseEntity.status(e.getStatusCode()).body("User Management service error: " + e.getMessage());
            } catch (ResourceAccessException e) {
                log.info("Failed to reach User Management service: {}");
                return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Service Unavailable: " + e.getMessage());
            } catch (Exception e) {
                log.info("Unexpected error while calling User Management service: {}");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
            }
        }

    private OtpRequest buildOtpRequest(String token) {
       OtpRequest otpRequest = new OtpRequest();
        TokenInfoDto tokenInfo = tokenCache.getTokenFromCache(token);
        UserDTO userDTO = userSessionCache.getUserFromSessionCache(tokenInfo);
        otpRequest.setEmail(userDTO.getEmail());
        otpRequest.setFirstName(userDTO.getFirstName());

        return otpRequest;
    }



    public ResponseEntity<String> redirectOtpVerificationRequest(ReaderVerifyOtpRequest otpRequest , String token)  {
        try{

            VerifyOtpRequest verifyOtpRequest = buildOtpVerificationRequest(token , otpRequest.getOtp());
            if (verifyOtpRequest == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Session");
            ResponseEntity<String> response =  restTemplate.postForEntity(urlServiceLocator.getVerifyOtpServiceUrl(), verifyOtpRequest, String.class);

            return response;
        } catch (HttpClientErrorException e) {
            log.info("Client error while calling User Management service: {}");
            return ResponseEntity.status(e.getStatusCode()).body("Access Denied: " + e.getMessage());
        } catch (HttpServerErrorException e) {
            log.info("Server error from User Management service: {}");
            return ResponseEntity.status(e.getStatusCode()).body("User Management service error: " + e.getMessage());
        } catch (ResourceAccessException e) {
            log.info("Failed to reach User Management service: {}");
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body("Service Unavailable: " + e.getMessage());
        } catch (Exception e) {
            log.info("Unexpected error while calling User Management service: {}");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }

    }

    private VerifyOtpRequest buildOtpVerificationRequest(String token , String otp) {

        try {
            UserDTO userDTO = userSessionCache.getUserFromSessionCache(tokenCache.getTokenFromCache(token));
            VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
            verifyOtpRequest.setEmail(userDTO.getEmail());
            verifyOtpRequest.setOtp(otp);
            return verifyOtpRequest;
        } catch (Exception e) {
            log.info("User DTO Is Empty  {}");
            e.printStackTrace();
            return null;
        }
    }
}
