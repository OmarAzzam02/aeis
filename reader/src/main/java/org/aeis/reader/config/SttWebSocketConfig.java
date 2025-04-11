package org.aeis.reader.config;


import org.aeis.reader.service.socket.SttPushWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class SttWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SttPushWebSocketHandler sttPushWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(sttPushWebSocketHandler, "/stt-stream")
                .setAllowedOrigins("*");

        registry.addHandler(sttPushWebSocketHandler, "/stt")
                .setAllowedOrigins("*");
    }






}
