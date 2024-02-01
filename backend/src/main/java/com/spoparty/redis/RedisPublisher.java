package com.spoparty.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.spoparty.api.party.dto.ChatRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisPublisher {
	private final RedisTemplate<String, ChatRequestDto> redisTemplate;

	private final ChannelTopic channelTopic;

	public void publish(ChannelTopic topic, ChatRequestDto message) {
		redisTemplate.convertAndSend(channelTopic.getTopic(), message);
	}

}