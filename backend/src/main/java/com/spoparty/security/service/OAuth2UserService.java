package com.spoparty.security.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.spoparty.api.member.entity.Member;
import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.security.model.PrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("oAuth2User: {}", oAuth2User);

		// kakao에서 넘어온 데이터 파싱
		String provider = userRequest.getClientRegistration().getRegistrationId();
		String id = oAuth2User.getAttributes().get("id") + "";
		Map<String, Object> userInfo = (Map<String, Object>)oAuth2User.getAttribute("kakao_account");
		String email = (String)userInfo.get("email");
		userInfo = (Map<String, Object>)userInfo.get("profile");
		String nickname = (String)userInfo.get("nickname");

		// 회원가입이 되어있는지 확인하고, 안되어있다면 회원가입
		Member member = memberRepository.findByLoginId(provider + "_" + id, Member.class).orElse(null);
		if (member == null) {
			member = new Member();
			member.setLoginId(provider + "_" + id);
			member.setNickname(nickname);
			member.setEmail(email);
			member.setLoginPwd(bCryptPasswordEncoder.encode(id));
			member.setProvider(provider);
			member = memberRepository.save(member);
			log.info("OAuth 회원가입 완료 : {}", member);
		} else {
			log.info("OAuth 로그인 : {}", member);
		}

		return new PrincipalDetails(member);
	}

}
