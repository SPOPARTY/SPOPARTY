package com.spoparty.redis;

import java.util.List;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spoparty.api.party.dto.ChatEnterRequestDto;
import com.spoparty.api.party.dto.ChatRequestDto;
import com.spoparty.api.party.dto.RedisDataDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final SimpMessagingTemplate messagingTemplate;
	private final String SUBSCRIBE_DESTINATION = "/sub/chat";
	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			log.debug("redis 발행 데이터 : {}",message);
			String channel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
			RedisDataDto redisDataDto = (RedisDataDto) redisTemplate.getValueSerializer().deserialize(message.getBody());

			if (redisDataDto.getDataType().equals(DataType.ENTER)) {
				processEnterMessage(channel, redisDataDto);
			} else if (redisDataDto.getDataType().equals(DataType.MESSAGE)) {
				processSendMessage(channel, redisDataDto);
			} else if (redisDataDto.getDataType().equals(DataType.OUT)) {
				processOutMessage(channel, redisDataDto);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	public void processEnterMessage(String channel, RedisDataDto redisDataDto) {
		ChatEnterRequestDto chatEnterRequestDto = (ChatEnterRequestDto) redisDataDto.getData();

		messagingTemplate.convertAndSend(SUBSCRIBE_DESTINATION, "test");
	}
	public void processSendMessage(String channel, RedisDataDto redisDataDto) {
		ChatRequestDto chatRequestDto = (ChatRequestDto)redisDataDto.getData();
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();

		listOperations.rightPush(channel, chatRequestDto);
		if (chatRequestDto.getType().equals(SubscribeType.USER)) {
			long chatLogSize = listOperations.size(channel);
			List<Object> list = listOperations.range(channel, 0, chatLogSize);

			messagingTemplate.convertAndSendToUser(chatRequestDto.getUserName(),SUBSCRIBE_DESTINATION, list);
		} else {
			// 채팅 채널로 BROAD CAST
			messagingTemplate.convertAndSend(SUBSCRIBE_DESTINATION, chatRequestDto);
		}
	}
	public void processOutMessage(String channel, RedisDataDto redisDataDto) {

	}
}