package com.spoparty.common.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StompPreHandler implements ChannelInterceptor {
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		log.debug(">>>> preSend !! message : {}", message);
		log.debug(">>>> preSend !! channel : {}", channel);
		return ChannelInterceptor.super.preSend(message, channel);
	}
}
