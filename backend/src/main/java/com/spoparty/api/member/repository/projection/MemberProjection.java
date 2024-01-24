package com.spoparty.api.member.repository.projection;

public interface MemberProjection {

	Long getId();

	String getLoginId();

	String getLoginPwd();

	String getNickname();

	String getEmail();

	String getProvider();

	String getRoleName();

	int getStatus();

}
