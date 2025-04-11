package org.aeis.tts.config;

import org.aeis.tts.service.WebSocketSender;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;


@Configuration
public class WebSocketConfig {

    private static final String BACKEND_URI = "ws://localhost:8080/tts-stream";

    private final AtomicReference<WebSocketSession> sessionRef = new AtomicReference<>();

    private final Logger log = org.slf4j.LoggerFactory.getLogger(WebSocketConfig.class);




    @Bean
    public WebSocketSender webSocketSender() {
        WebSocketClient client = new StandardWebSocketClient();

        URI uri = URI.create(BACKEND_URI);
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();

        client.doHandshake(new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) {
                log.info("✅ WebSocket connected to main backend");
                sessionRef.set(session);
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) {
                System.err.println("WebSocket error: " + exception.getMessage());
                sessionRef.set(null);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
                log.error("❌ WebSocket connection closed");
                sessionRef.set(null);
            }
        }, headers, uri);

        return new WebSocketSender(sessionRef);
    }

}
