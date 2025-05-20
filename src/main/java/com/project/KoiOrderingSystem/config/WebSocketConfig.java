package com.project.KoiOrderingSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/api/messages/chat") // endpoint này phải trùng với SockJS URL
                .setAllowedOriginPatterns("*")     // Cho phép mọi domain
                .withSockJS();                      // Dùng SockJS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // nơi client gửi message đến (ví dụ /app/chat)
        registry.enableSimpleBroker("/queue", "/topic");    // nơi server gửi lại message
        registry.setUserDestinationPrefix("/user");         // xử lý cho user riêng lẻ
    }
}
