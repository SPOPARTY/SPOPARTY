package com.spoparty.api.party.service;

import com.spoparty.api.party.dto.ChatEnterRequestDto;
import com.spoparty.api.party.dto.ChatOutRequestDto;
import com.spoparty.api.party.dto.ChatRequestDto;

interface ChatService {
	void enter(ChatEnterRequestDto chatRequestDto);
	void out(ChatOutRequestDto chatRequestDto);
	void sendChatAll();
	void sendChat(ChatRequestDto chatRequestDto);
}
