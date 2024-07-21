package com.dmrj.dm_resqjeevan.helpers;

import com.dmrj.dm_resqjeevan.dto.WebSocketDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionRegistry {
    private final Map<String,WebSocketSession> sessions = new ConcurrentHashMap<>();
    public void addSession(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public WebSocketSession getSession(String username) {
        return sessions.get(username);
    }
    public void removeSession(String username) {
        sessions.remove(username);
    }
}
