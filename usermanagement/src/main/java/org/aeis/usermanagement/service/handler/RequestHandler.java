package org.aeis.usermanagement.service.handler;


import org.aeis.usermanagement.dto.UserDTO;
import org.aeis.usermanagement.dto.UserLoginDto;
import org.aeis.usermanagement.dto.LoginResponse;

import org.aeis.usermanagement.service.jwt.JwtService;
import org.aeis.usermanagement.service.user.LoginService;
import org.aeis.usermanagement.service.user.UserInfoRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RequestHandler {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserInfoRetrievalService userInfoRetrievalService;

    @Autowired
    private JwtService jwtService;
    public ResponseEntity<LoginResponse> handleLogin(UserLoginDto loginDto) {
        return loginService.login(loginDto)
                .map(user -> ResponseEntity.ok(createLoginResponse(true, jwtService.generateTokenWithUserInfo(user), "Login successful")))
                .orElseGet(() -> ResponseEntity.badRequest().body(createLoginResponse(false, "", "User not found")));
    }


    public ResponseEntity<?> handleUserInfo(Long userId) {
        try {
            UserDTO userDTO = userInfoRetrievalService.getUserInfo(userId);
            if (userDTO != null)
                return ResponseEntity.ok(userDTO);


            return ResponseEntity.badRequest().body("User Does no exist");

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error while getting user info");
        }


    }




    private LoginResponse createLoginResponse(boolean status, String token, String message) {
        return LoginResponse.builder()
                .status(status)
                .token(token)
                .message(message)
                .build();
    }






}
