package org.aeis.reader.service.socket;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SttPushWebSocketHandler extends TextWebSocketHandler {


    private final Logger log = org.slf4j.LoggerFactory.getLogger(SttPushWebSocketHandler.class);

    private final Set<WebSocketSession> studentSessions = ConcurrentHashMap.newKeySet();


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String text = message.getPayload();
        log.info("📥 Received TTS data: " + text);
        broadcastToAll(text);
        log.info("📤 Sent TTS data to all students: " + text);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        studentSessions.add(session);
        log.info(" Connected to the STT microservice {} ", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        studentSessions.remove(session);
        log.info(" Disconnected from STT Microservice {} ", session.getId());
    }


    public void broadcastToAll(String message) {
        studentSessions.stream()
                .filter(WebSocketSession::isOpen)
                .forEach(session -> {
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }


}
