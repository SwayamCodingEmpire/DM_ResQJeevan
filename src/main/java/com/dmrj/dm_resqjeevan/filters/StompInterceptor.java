package com.dmrj.dm_resqjeevan.filters;

import com.dmrj.dm_resqjeevan.helpers.WebSocketSessionRegistry;
import com.dmrj.dm_resqjeevan.services.serviceImpl.JwtServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
@Component
public class StompInterceptor implements HandshakeInterceptor {

    private final Logger logger = LoggerFactory.getLogger(StompInterceptor.class);


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            Enumeration<String> protocols = httpServletRequest.getHeaders("Sec-WebSocket-Protocol");
            if (protocols.hasMoreElements()) {
                String protocol = protocols.nextElement();
                logger.info("Sec-WebSocket-Protocol: {}", protocol);
            } else {
                logger.info("Sec-WebSocket-Protocol header is not present.");
            }

            String token = String.valueOf(servletRequest.getServletRequest().getHeader("Connection"));
            logger.info("Before handshake" + token);
            HttpSession session = servletRequest.getServletRequest().getSession();
            System.out.println("Before handshake: " + session.getId());
            attributes.put("sessionId", session.getId());
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

            // Retrieve the session ID directly from the request
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                String sessionId = session.getId();
                System.out.println("After handshake: Http Session ID: " + sessionId);
            } else {
                System.out.println("After handshake: Http Session not found");
            }
        }
        }

    }
