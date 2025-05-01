package org.aeis.usermanagement.service.handler;


import jakarta.servlet.http.HttpServletRequest;
import org.aeis.usermanagement.cache.UserInfoCache;
import org.aeis.usermanagement.dto.UserDTO;
import org.aeis.usermanagement.dto.UserLoginDto;
import org.aeis.usermanagement.dto.LoginResponse;

import org.aeis.usermanagement.dto.ValidTokenRequest;
import org.aeis.usermanagement.dto.session.TokenInfoDto;
import org.aeis.usermanagement.dto.session.UserSessionDto;
import org.aeis.usermanagement.entity.User;
import org.aeis.usermanagement.service.jwt.JwtService;
import org.aeis.usermanagement.service.jwt.ParseRequestHeader;
import org.aeis.usermanagement.service.user.LoginService;
import org.aeis.usermanagement.service.user.UserInfoRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestHandler {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserInfoRetrievalService userInfoRetrievalService;

    @Autowired
    private ParseRequestHeader parser;
    @Autowired
    private JwtService jwtService;


    @Autowired
    private UserInfoCache userInfoCache;



    public ResponseEntity<LoginResponse> handleLogin(UserLoginDto loginDto) {

        Optional<User> user = loginService.login(loginDto);
        if (user.isPresent())
            return ResponseEntity.ok(createLoginResponse(user.get().getRole().name(), jwtService.generateTokenWithUserInfo(user.get()), "Login successful"));


        return ResponseEntity.badRequest().body(createLoginResponse("UNAUTHORIZED", "", "User not found"));
    }


    public ResponseEntity<?> handleUserInfo(String token) {
        try {
            Long userId = jwtService.extractUserId(token);
            UserDTO cachedDTO = userInfoCache.getUserInfoFromCache(userId);
            if (cachedDTO != null)
                return ResponseEntity.ok(cachedDTO);

            UserDTO userDTO = userInfoRetrievalService.getUserInfo(userId);

            if (userDTO != null)
                return ResponseEntity.ok(userDTO);

            return ResponseEntity.badRequest().body("User Does no exist");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while getting user info");
        }


    }


    private LoginResponse createLoginResponse(String role, String token, String message) {
        return LoginResponse.builder()
                .role(role)
                .token(token)
                .message(message)
                .build();
    }


    public ResponseEntity<?> handleTokenValidation(HttpServletRequest request) {
        try {
            String token = parser.parseHeader(request);
            if (!jwtService.isTokenValid(token))
                return ResponseEntity.badRequest().body("Invalid token");


            Long userId = jwtService.extractUserId(token);
            UserDTO userDTO = userInfoCache.getUserInfoFromCache(userId);

            if (userDTO == null) {
                userDTO = extractUserFromToken(token);
            }

            TokenInfoDto tokenInfoDto = buildTokenInfoDto(token);
            UserSessionDto userSessionDto  = buildUserSessionDto(userDTO, tokenInfoDto);

            return ResponseEntity.ok(userSessionDto);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while validating token");
        }

    }

    private UserSessionDto buildUserSessionDto(UserDTO userDTO, TokenInfoDto tokenInfoDto) {
        return UserSessionDto.builder()
                .userInfo(userDTO)
                .tokenInfo(tokenInfoDto)
                .build();
    }

    private TokenInfoDto buildTokenInfoDto(String token) {
        return TokenInfoDto.builder()
                .token(token)
                .expireDate(((long) jwtService.getExpirationTime()))
                .build();
    }

    //TODO
    private UserDTO extractUserFromToken(String token){
        Long userId = jwtService.extractUserId(token);
        return userInfoRetrievalService.getUserInfo(userId);
    }
}
