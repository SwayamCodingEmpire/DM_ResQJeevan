package com.dmrj.dm_resqjeevan.config;

import com.dmrj.dm_resqjeevan.filters.JwtAuthenticationFilter;
import com.dmrj.dm_resqjeevan.filters.SessionChannelInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;


@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SessionChannelInterceptor sessionChannelInterceptor;
    private final HttpMessageConverters messageConverters;

    public WebSocketConfig(JwtAuthenticationFilter jwtAuthenticationFilter, SessionChannelInterceptor sessionChannelInterceptor, HttpMessageConverters messageConverters) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.sessionChannelInterceptor = sessionChannelInterceptor;
        this.messageConverters = messageConverters;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/server1")
                .setAllowedOrigins("http://127.0.0.1:49865","http://127.0.0.1:50296")
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(sessionChannelInterceptor);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> converters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        converters.add(converter);
        return false;
    }


    @Bean
    public TaskScheduler heartBeatTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

}
