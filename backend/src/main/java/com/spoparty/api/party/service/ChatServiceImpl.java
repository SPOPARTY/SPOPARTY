package com.spoparty.api.party.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.spoparty.api.party.dto.ChatEnterRequestDto;
import com.spoparty.api.party.dto.ChatOutRequestDto;
import com.spoparty.api.party.dto.ChatRequestDto;
import com.spoparty.api.party.dto.ChatResponseDto;
import com.spoparty.api.party.dto.RedisDataDto;
import com.spoparty.redis.DataType;
import com.spoparty.redis.RedisPublisher;
import com.spoparty.redis.RedisSubscriber;
import com.spoparty.redis.SubscribeType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

	private final RedisMessageListenerContainer redisMessageListenerContainer;
	private final RedisSubscriber redisSubscriber;
	private final RedisPublisher redisPublisher;
	private Map<String, ChannelTopic> channels = new HashMap<>();

	@Override
	public void enter(ChatEnterRequestDto chatEnterRequestDto) {
		ChannelTopic topic = channels.get("chat");
		if (topic == null) {
			topic = new ChannelTopic("chat");
			redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
			chatEnterRequestDto.setType(SubscribeType.BROAD_CAST);
		} else {
			chatEnterRequestDto.setType(SubscribeType.USER);
		}
		channels.put("chat", topic);

		RedisDataDto.RedisDataDtoBuilder<Object> redisDataDto =
			RedisDataDto
				.builder()
				.dataType(DataType.ENTER)
				.data(chatEnterRequestDto);

		redisPublisher.publish(topic, redisDataDto);
	}

	@Override
	public void out(ChatOutRequestDto chatOutRequestDto) {

	}

	@Override
	public void sendChatAll() {

	}

	@Override
	public void sendChat(ChatRequestDto chatRequestDto) {
		ChannelTopic topic = channels.get("chat");
		if (topic == null) {
			// 오류 처리
		}

		RedisDataDto.RedisDataDtoBuilder<Object> redisDataDto =
			RedisDataDto
				.builder()
				.dataType(DataType.ENTER)
				.data(chatRequestDto);

		redisPublisher.publish(topic, redisDataDto);
	}
}
