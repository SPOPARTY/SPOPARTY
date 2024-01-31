package com.spoparty.api.party.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spoparty.redis.RedisPublisher;
import com.spoparty.redis.RedisSubscriber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessagingTemplate template;
	private final RedisTemplate redisTemplate;
	private final RedisMessageListenerContainer redisMessageListenerContainer;
	private final RedisSubscriber redisSubscriber;
	private final RedisPublisher redisPublisher;

	@MessageMapping("chat/enter")
	@SendTo("/sub")
	public String enter(String chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatMessage);
		// Websocket에 발행된 메시지를 redis로 발행(publish)
		ChannelTopic topic = new ChannelTopic("chat");
		redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
		redisPublisher.publish(topic, chatMessage);
		return chatMessage;
	}

	@MessageMapping("chat/out")
	@SendTo("/sub")
	public String out(String chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatMessage);
		// Websocket에 발행된 메시지를 redis로 발행(publish)
		ChannelTopic topic = new ChannelTopic("chat");
		redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
		redisPublisher.publish(topic, chatMessage);
		return chatMessage;
	}

	@MessageMapping("chat/send")
	@SendTo("/sub")
	public String send(String chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatMessage);
		// Websocket에 발행된 메시지를 redis로 발행(publish)
		ChannelTopic topic = new ChannelTopic("chat");
		redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
		redisPublisher.publish(topic, chatMessage);
		return chatMessage;
	}
}
