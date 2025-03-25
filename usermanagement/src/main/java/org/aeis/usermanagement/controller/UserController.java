package org.aeis.usermanagement.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.usermanagement.dto.UserLoginDto;
import org.aeis.usermanagement.dto.LoginResponse;
import org.aeis.usermanagement.dto.ValidTokenRequest;
import org.aeis.usermanagement.service.handler.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UserController {


    @Autowired
    private RequestHandler requestHandler;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginDto loginDto) {


        ResponseEntity<LoginResponse> response = requestHandler.handleLogin(loginDto);
        return response;
    }

    @GetMapping("/users/info/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
        return requestHandler.handleUserInfo(userId);
    }


        @PostMapping("validate/token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        return requestHandler.handleTokenValidation(request);

    }


}
