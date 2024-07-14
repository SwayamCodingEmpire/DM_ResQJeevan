package com.dmrj.dm_resqjeevan.config;

import com.dmrj.dm_resqjeevan.filters.JwtAuthenticationFilter;
import com.dmrj.dm_resqjeevan.filters.SessionChannelInterceptor;
import com.dmrj.dm_resqjeevan.filters.StompInterceptor;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final StompInterceptor stompInterceptor;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SessionChannelInterceptor sessionChannelInterceptor;

    public WebSocketConfig(StompInterceptor stompInterceptor, JwtAuthenticationFilter jwtAuthenticationFilter, SessionChannelInterceptor sessionChannelInterceptor) {
        this.stompInterceptor = stompInterceptor;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.sessionChannelInterceptor = sessionChannelInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
        registry.addEndpoint("/server1")
                .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy))
                .setAllowedOrigins("http://127.0.0.1:62870","http://127.0.0.1:52443")
                .addInterceptors(stompInterceptor)
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/topic","/queue")
                .setTaskScheduler(heartBeatTaskScheduler())
                .setHeartbeatValue(new long[] {10000L,10000L});
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(sessionChannelInterceptor);
    }


    @Bean
    public TaskScheduler heartBeatTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

}
