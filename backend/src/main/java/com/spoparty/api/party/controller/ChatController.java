package com.spoparty.api.party.controller;

import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.spoparty.api.party.dto.ChatEnterRequestDto;
import com.spoparty.api.party.dto.ChatOutRequestDto;
import com.spoparty.api.party.service.ChatServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {


	private final ChatServiceImpl chatService;

	@MessageMapping("chat/enter")
	public void enter(@RequestBody ChatEnterRequestDto chatEnterRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatEnterRequestDto);

		chatEnterRequestDto.setSessionId(simpMessageHeaderAccessor.getSessionId());
		// Websocket에 발행된 메시지를 redis로 발행(publish)
		chatService.enter(chatEnterRequestDto);
	}

	@MessageMapping("chat/out")
	public void out(ChatOutRequestDto chatOutRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatOutRequestDto);

		// Websocket에 발행된 메시지를 redis로 발행(publish)
	}

	@MessageMapping("chat/send")
	public void send(@RequestBody ChatEnterRequestDto chatRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("received message : {}", chatRequestDto);
		// Websocket에 발행된 메시지를 redis로 발행(publish)
	}
}
