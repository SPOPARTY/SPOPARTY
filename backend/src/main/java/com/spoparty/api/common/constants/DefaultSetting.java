package com.spoparty.api.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DefaultSetting {
	INVITE_EXPIRATION_PERIOD("7일", 7L),
	INVITE_URL_DOMAIN("초대 링크 도메인", "https://i10a802.p.ssafy.io/invite/");

	private final String explanation;
	private final Object value;
}
