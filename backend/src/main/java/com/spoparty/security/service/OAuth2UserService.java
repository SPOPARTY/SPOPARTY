package com.spoparty.security.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.spoparty.api.member.repository.MemberRepository;

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
		log.info("@@@@@@@@@@@@getAttribute(): {}", oAuth2User.getAttributes());

		String provider = userRequest.getClientRegistration().getClientId();
		String providerId = (String)oAuth2User.getAttributes().get("id");

		Map<String, Object> map = (Map<String, Object>)((Map<String, Object>)oAuth2User.getAttributes()
			.get("properties")).get("kakao_account");
		log.info("map: {}", map);

		// 강제 회원가입
		// String provider = userRequest.getClientRegistration().getClientId();
		// String providerId = oAuth2User.getAttribute("sub");
		// String username = provider+"_"+providerId;
		// String email = oAuth2User.getAttribute("email");
		// String password = bCryptPasswordEncoder.encode("겟인데어");
		// String role = "ROLE_USER";
		//
		// User user = userRepository.findByUsername(username);
		// System.out.println("asdasd "+username);
		// if(user == null) {
		// 	System.out.println("asdasd 자동로그인");
		// 	user = User.builder()
		// 			.username(username)
		// 			.email(email)
		// 			.password(password)
		// 			.role(role)
		// 			.provider(providerId)
		// 			.providerId(providerId)
		// 			.build();
		// 	userRepository.save(user);
		// }else {
		// 	System.out.println("asdasd 이미로그인");
		// }
		// return new PrincipalDetails(user, oAuth2User.getAttributes());
		return null;
	}

}
