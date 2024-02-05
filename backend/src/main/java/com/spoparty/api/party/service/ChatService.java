package com.spoparty.api.party.service;

import com.spoparty.api.party.dto.ChatRequestDto;

interface ChatService {
	void enter(ChatRequestDto chatRequestDto);
	void out(ChatRequestDto chatRequestDto);
	void sendChat(ChatRequestDto chatRequestDto);
}
