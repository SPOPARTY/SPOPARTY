package com.spoparty.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoparty.api.party.dto.ChatRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, ChatRequestDto> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;

	/**
	 * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
	 */

	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			// redis에서 발행된 데이터를 받아 deserialize
			log.debug("redis 발행 데이터 : {}",message);
			String channel = (String) redisTemplate.getStringSerializer().deserialize(message.getChannel());
			ChatRequestDto chatRequestDto = (ChatRequestDto) redisTemplate.getValueSerializer().deserialize(message.getBody());
			// ChatMessage 객채로 맵핑
			// Websocket 구독자에게 채팅 메시지 Send
			ListOperations<String, ChatRequestDto> listOperations = redisTemplate.opsForList();
			listOperations.rightPush(channel, chatRequestDto);
			messagingTemplate.convertAndSendToUser(chatRequestDto.getUserName(),"/sub/chat", "유저가 보낸 redis user message");
			// messagingTemplate.convertAndSend("/sub/chat", "redis message");

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}