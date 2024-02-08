package com.spoparty.api.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationMessage {
	START_PARTY("그룹 파티에 초대합니다!", "파티에 함께해요! 재미와 스포츠, 그리고 좋은 친구들이 기다리고 있어요. 놓치지 마세요!");

	private final String title;
	private final String content;
}