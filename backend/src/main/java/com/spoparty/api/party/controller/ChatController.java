package com.spoparty.api.party.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.spoparty.api.party.dto.request.ChatRequestDto;
import com.spoparty.api.party.service.ChatServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

	private final ChatServiceImpl chatService;

	@MessageMapping("chat/enter")
	public void enter(@RequestBody ChatRequestDto chatRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("/chat/enter received message : {}", chatRequestDto);

		// Websocket에 발행된 메시지를 redis로 발행(publish)
		chatService.enter(chatRequestDto);
	}

	@MessageMapping("chat/out")
	public void out(ChatRequestDto chatRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("/chat/out received message : {}", chatRequestDto);

		chatService.out(chatRequestDto);
	}

	@MessageMapping("chat/send")
	public void send(@RequestBody ChatRequestDto chatRequestDto, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		log.debug("/chat/send received message : {}", chatRequestDto);

		chatService.sendChat(chatRequestDto);
	}
}
