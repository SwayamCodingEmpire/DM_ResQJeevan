package com.dmrj.dm_resqjeevan.filters;

import com.dmrj.dm_resqjeevan.helpers.WebSocketSessionRegistry;
import com.dmrj.dm_resqjeevan.services.serviceImpl.OnlineOfflineServiceImpl;
import com.dmrj.dm_resqjeevan.services.serviceImpl.JwtServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class SessionChannelInterceptor implements ChannelInterceptor {
    private final WebSocketSessionRegistry webSocketSessionRegistry;
    private final OnlineOfflineServiceImpl currentlyOnlineService;
    private final StompInterceptor  stompInterceptor;
    private final Logger logger = LoggerFactory.getLogger(SessionChannelInterceptor.class);
    private final JwtServiceImpl jwtServiceImpl;
    private final UserDetailsService userDetailsService;

    public SessionChannelInterceptor(WebSocketSessionRegistry webSocketSessionRegistry, OnlineOfflineServiceImpl currentlyOnlineService, StompInterceptor stompInterceptor, JwtServiceImpl jwtServiceImpl, UserDetailsService userDetailsService) {
        this.webSocketSessionRegistry = webSocketSessionRegistry;
        this.currentlyOnlineService = currentlyOnlineService;
        this.stompInterceptor = stompInterceptor;
        this.jwtServiceImpl = jwtServiceImpl;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        logger.info("SessionChannelInterceptor.preSend");
        MessageHeaders messageHeaders = message.getHeaders();
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(StompCommand.CONNECT.equals(accessor.getCommand())){
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
                if(username != null && authentication == null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if(jwtServiceImpl.isTokenValid(jwtToken, userDetails)){
                        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                        accessor.setUser(authenticationToken);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
//            MultiValueMap<String,String> multiValueMap = messageHeaders.get(StompHeaderAccessor.NATIVE_HEADERS,MultiValueMap.class);
//            for (Map.Entry<String, List<String>> head : multiValueMap.entrySet()) {
//                logger.info(head.getKey() + "#" + head.getValue());
//            }
        }

//        try {
//            final String jwtToken = jwtTokenFull.substring(7);
//            logger.info("jwtToken: " + jwtToken);
//            final String username = jwtServiceImpl.extractUsername(jwtToken);
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if(username != null && authentication==null) {
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                if(jwtServiceImpl.isTokenValid(jwtToken, userDetails)) {
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
//                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails());
//                }
//            }
//        }
        return message;
    }
}
