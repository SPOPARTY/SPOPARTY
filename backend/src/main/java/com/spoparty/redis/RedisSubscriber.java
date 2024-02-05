package com.spoparty.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.spoparty.api.party.dto.ChatRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

	private final RedisTemplate<String, ChatRequestDto> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;

	private final String SUBSCRIBE_DESTINATION = "/sub/chat";
	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			log.debug("redis 발행 데이터 : {}",message);
			String channel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
			ChatRequestDto request = (ChatRequestDto) redisTemplate.getValueSerializer().deserialize(message.getBody());
			ListOperations<String, ChatRequestDto> operations = redisTemplate.opsForList();

			operations.rightPush(channel, request);

			if (request.getType().equals(SubscribeType.BROAD_CAST)) {
				messagingTemplate.convertAndSend(SUBSCRIBE_DESTINATION, request);
			} else if (request.getType().equals(SubscribeType.USER)) {
				long size = operations.size(channel);
				List<ChatRequestDto> list = operations.range(channel, 0, size);
				messagingTemplate.convertAndSendToUser(request.getUserName(), SUBSCRIBE_DESTINATION, list);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}