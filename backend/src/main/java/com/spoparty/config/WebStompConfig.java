package com.spoparty.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.spoparty.common.interceptor.StompPreHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebStompConfig implements WebSocketMessageBrokerConfigurer {
	@Autowired
	private StompPreHandler stompPreHandler;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			.addEndpoint("/ws-stomp")
			.setAllowedOrigins("https://i10a802.p.ssafy.io", "http://localhost:5173")
			.withSockJS(); // endpoint
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/chat", "/sub", "/user", "/vote"); // sub
		registry.setApplicationDestinationPrefixes("/"); // pub
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) { // 웹소켓 interceptor
		registration.interceptors(stompPreHandler);
	}
}