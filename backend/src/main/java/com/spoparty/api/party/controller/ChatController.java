package com.spoparty.api.party.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spoparty.api.party.dto.ChatRequestDto;
import com.spoparty.redis.RedisPublisher;
import com.spoparty.redis.RedisSubscriber;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessagingTemplate template;
	private final RedisTemplate<String, ChatRequestDto> redisTemplate;
	private final RedisMessageListenerContainer redisMessageListenerContainer;
	private final RedisSubscriber redisSubscriber;
	private final RedisPublisher redisPublisher;
	private Map<String, ChannelTopic> channels = new HashMap<>();

	@MessageMapping("chat/enter")
	@SendTo("/sub")
	public void enter(@RequestBody ChatRequestDto chatRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatRequestDto);
		log.debug("session : {}", simpMessageHeaderAccessor.getSessionId());
		chatRequestDto.setSessionId(simpMessageHeaderAccessor.getSessionId());
		// Websocket에 발행된 메시지를 redis로 발행(publish)
		ChannelTopic topic = channels.get("chat");
		if (topic == null) {
			topic = new ChannelTopic("chat");
			redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
		} else {
			// 해당 토픽의 전체 데이터 가져오기
			ListOperations<String, ChatRequestDto> listOperations = redisTemplate.opsForList();
			Long chatLogSize = listOperations.size(topic.getTopic());
			List<ChatRequestDto> list = listOperations.range(topic.getTopic(), 0, chatLogSize);

		}

		channels.put("chat", topic);

		redisPublisher.publish(topic, chatRequestDto);
	}

	@MessageMapping("chat/out")
	@SendTo("/sub")
	public String out(String chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatMessage);
		String userName = simpMessageHeaderAccessor.getUser().getName();
		// Websocket에 발행된 메시지를 redis로 발행(publish)
		return chatMessage;
	}

	@MessageMapping("chat/send")
	@SendTo("/sub")
	public void send(@RequestBody ChatRequestDto chatRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatRequestDto);
		// Websocket에 발행된 메시지를 redis로 발행(publish)
		ChannelTopic topic = channels.get("chat");
		if (topic == null) {
			// 오류 처리
		}
		redisPublisher.publish(topic, chatRequestDto);
	}
}
