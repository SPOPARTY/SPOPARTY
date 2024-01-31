package com.spoparty.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisPublisher {
	private final RedisTemplate<String, Object> redisTemplate;

	private final ChannelTopic channelTopic;

	public void publish(ChannelTopic topic, String message) {
		redisTemplate.convertAndSend(channelTopic.getTopic(), message);
	}
}