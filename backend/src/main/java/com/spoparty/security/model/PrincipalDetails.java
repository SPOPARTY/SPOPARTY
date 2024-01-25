package com.spoparty.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.spoparty.api.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalDetails implements UserDetails {

	private Member member;

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

}
