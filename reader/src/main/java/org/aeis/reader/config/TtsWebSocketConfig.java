package org.aeis.reader.config;


import org.aeis.reader.service.socket.SttPushWebSocketHandler;
import org.aeis.reader.service.socket.TtsPushWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class TtsWebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private TtsPushWebSocketHandler ttsPushWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(ttsPushWebSocketHandler, "/tts-stream")
                .setAllowedOrigins("*");

        registry.addHandler(ttsPushWebSocketHandler, "/tts")
                .setAllowedOrigins("*");

    }

    }
