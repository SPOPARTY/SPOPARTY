package com.spoparty.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import com.spoparty.api.party.dto.ChatEnterRequestDto;
import com.spoparty.api.party.dto.RedisDataDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisPublisher {

	private final RedisTemplate<String, Object> redisTemplate;

	public void publish(ChannelTopic topic, Object message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}

}