package org.aeis.usermanagement.controller;


import org.aeis.usermanagement.dto.UserDTO;
import org.aeis.usermanagement.dto.UserLoginDto;
import org.aeis.usermanagement.response.LoginResponse;
import org.aeis.usermanagement.service.user.InstructorInfoService;
import org.aeis.usermanagement.service.user.UserInfoRetrievalService;
import org.aeis.usermanagement.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserInfoRetrievalService userInfoRetrievalService;

    @Autowired
    private InstructorInfoService instructorInfoService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginDto loginDto) {
        LoginResponse loginResponse = loginService.login(loginDto);
        if(loginResponse.isStatus())
            return ResponseEntity.ok(loginResponse);
        else
            return ResponseEntity.badRequest().body(loginResponse);

    }

    @GetMapping("/users/info/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
        try {
            UserDTO userDTO = userInfoRetrievalService.getUserInfo(userId);
          if (userDTO != null)
            return ResponseEntity.ok(userDTO);

            else
                return ResponseEntity.badRequest().body("Error while getting user info");

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while getting student info");
        }


    }



}
