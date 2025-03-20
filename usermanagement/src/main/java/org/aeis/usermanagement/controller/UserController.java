package org.aeis.usermanagement.controller;


import org.aeis.usermanagement.dto.UserLoginDto;
import org.aeis.usermanagement.dto.LoginResponse;
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
        return requestHandler.handleLogin(loginDto);
    }

    @GetMapping("/users/info/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
        return requestHandler.handleUserInfo(userId);
    }



}
