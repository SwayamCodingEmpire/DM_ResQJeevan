package com.dmrj.dm_resqjeevan.filters;

import com.dmrj.dm_resqjeevan.helpers.WebSocketSessionRegistry;
import com.dmrj.dm_resqjeevan.services.serviceImpl.JwtServiceImpl;
import com.dmrj.dm_resqjeevan.services.serviceImpl.LeaderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SessionChannelInterceptor implements ChannelInterceptor {
    private final WebSocketSessionRegistry webSocketSessionRegistry;
    private final Logger logger = LoggerFactory.getLogger(SessionChannelInterceptor.class);
    private final JwtServiceImpl jwtServiceImpl;
    private final UserDetailsService userDetailsService;
    private final LeaderServiceImpl leaderServiceImpl;

    public SessionChannelInterceptor(WebSocketSessionRegistry webSocketSessionRegistry, JwtServiceImpl jwtServiceImpl, UserDetailsService userDetailsService, LeaderServiceImpl leaderServiceImpl) {
        this.webSocketSessionRegistry = webSocketSessionRegistry;
        this.jwtServiceImpl = jwtServiceImpl;
        this.userDetailsService = userDetailsService;
        this.leaderServiceImpl = leaderServiceImpl;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        logger.info("SessionChannelInterceptor.preSend");
        MessageHeaders messageHeaders = message.getHeaders();
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            final String jwtTokenFull = accessor.getFirstNativeHeader("Authorization");
            logger.info("jwtTokenFull: " + jwtTokenFull);
            try {
                String websocketSessionId = (String) messageHeaders.get("simpSessionId");
                logger.info("websocketSessionId: " + websocketSessionId);
                WebSocketSession session = webSocketSessionRegistry.getSession(websocketSessionId);
                logger.info("session: " + session);
                final String jwtToken = jwtTokenFull.substring(7);
                logger.info("jwtToken: " + jwtToken);
                final String username = jwtServiceImpl.extractUsername(jwtToken);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (username != null && authentication == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (jwtServiceImpl.isTokenValid(jwtToken, userDetails)) {
                        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        accessor.setUser(authenticationToken);
                        logger.info(authenticationToken.getName());
                    }
                }
            } catch (Exception e) {
                logger.error("this is the problem");
                e.printStackTrace();
                return null;
            }
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand()) || StompCommand.UNSUBSCRIBE.equals(accessor.getCommand()) || StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            return message;
        }
        else {
                final String jwtTokenFull = accessor.getFirstNativeHeader("Authorization");
                logger.info(accessor.getCommand() + "     " + accessor.getFirstNativeHeader("Authorization"));
                try {
                    String websocketSessionId = (String) messageHeaders.get("simpSessionId");
                    logger.info("websocketSessionId: " + websocketSessionId);
                    WebSocketSession session = webSocketSessionRegistry.getSession(websocketSessionId);

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null) {
                        final String jwtToken = jwtTokenFull.substring(7);
                        logger.info("jwtToken: " + jwtToken);
                        final String username = jwtServiceImpl.extractUsername(jwtToken);
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                        if (jwtServiceImpl.isTokenValid(jwtToken, userDetails)) {
                            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            accessor.setUser(authenticationToken);
                            logger.info(authenticationToken.getName());
                        }
                    }
                } catch (Exception e) {
                    logger.error("else is the problem");
                    e.printStackTrace();
                    return null;
                }

            return message;
        }
        return message;
    }

}
