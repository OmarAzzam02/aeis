package org.aeis.stt.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


public class WebSocketSender {


    private final AtomicReference<WebSocketSession> sessionRef;
    private final Logger log = org.slf4j.LoggerFactory.getLogger(WebSocketSender.class);

    public WebSocketSender(AtomicReference<WebSocketSession> sessionRef) {
        this.sessionRef = sessionRef;
    }


    public void send(String text) {
        WebSocketSession session = sessionRef.get();

        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(text));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.error("❌ WebSocket not connected to backend");
        }
    }

}
