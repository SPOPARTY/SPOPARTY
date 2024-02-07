package com.spoparty.api.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationMessage {
	START_PARTY(" 그룹의 파티가 시작됐습니다.", " 그룹의 파티가 시작됐습니다.");

	private final String title;
	private final String content;
}
