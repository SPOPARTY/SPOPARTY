package com.spoparty.api.party.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import com.spoparty.api.party.dto.ChatRequestDto;
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
	public void enter(ChatRequestDto chatRequestDto) {
		String topicName = String.format("%s-%s", chatRequestDto.getClubId(), chatRequestDto.getPartyId());
		ChannelTopic topic = channels.get(topicName);
		if (topic == null) {
			topic = new ChannelTopic(topicName);
			redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);

			chatRequestDto.setType(SubscribeType.BROAD_CAST);
		} else {
			chatRequestDto.setType(SubscribeType.USER);
		}
		channels.put("chat", topic);

		redisPublisher.publish(topic, chatRequestDto);
	}

	@Override
	public void out(ChatRequestDto chatRequestDto) {
		ChannelTopic topic = channels.get("chat");
		if (topic == null) {
			// 오류 처리
		}

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

//		RedisDataDto.RedisDataDtoBuilder<Object> redisDataDto =
//			RedisDataDto
//				.builder()
//				.dataType(DataType.ENTER)
//				.data(chatRequestDto);

		redisPublisher.publish(topic, chatRequestDto);
	}
}
