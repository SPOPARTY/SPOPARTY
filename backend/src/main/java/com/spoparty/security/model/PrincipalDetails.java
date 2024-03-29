package com.spoparty.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.spoparty.api.member.entity.Member;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private Member member;
	private Map<String, Object> map;

	public PrincipalDetails(Member member) {
		this.member = member;
		Map<String, Object> tmp = new HashMap<>();
		tmp.put("id", member.getId());
		tmp.put("loginId", member.getLoginId());
		tmp.put("loginPwd", member.getLoginPwd());
		tmp.put("nickname", member.getNickname());
		map = tmp;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return map;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = member.getRoleName();
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return role;
			}
		});
		return list;
	}

	@Override
	public String getPassword() {
		return member.getLoginPwd();
	}

	@Override
	public String getUsername() {
		return member.getLoginId();
	}

	// status가 0이 아니면(1:휴면, 2:정지) 계정 비활성화 상태
	@Override
	public boolean isEnabled() {
		return member.getState() == 0;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getName() {
		return null;
	}
}
