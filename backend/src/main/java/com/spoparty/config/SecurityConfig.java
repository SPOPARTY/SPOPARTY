package com.spoparty.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spoparty.api.member.repository.MemberRepository;
import com.spoparty.security.jwt.JwtAuthenticationProvider;
import com.spoparty.security.service.PrincipalDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	// 시큐리티 설정파일 입니다.
	// 페이지 별 권한설정을 할 수 있습니다.

	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final PrincipalDetailService principalDetailService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final MemberRepository memberRepository;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.info("filterChain 실행");
		return http

			// .addFilterAfter(new JwtAuthenticationFilter(authenticationManager()),
			// 	UsernamePasswordAuthenticationFilter.class)
			// .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(), memberRepository),
			// 	UsernamePasswordAuthenticationFilter.class)

			// '/' 와 '/members/join 은 권한없이 접근가능
			// '/admin/' 은 권한이 ROLE_ADMIN인 사람만 접근 가능
			// 나머지 경로에 대해서 인증(로그인)된 사람만 접근 가능 ( 임시로 모두가능하게 설정 )
			.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
				.requestMatchers("/", "/members/join", "/members/login", "/login").permitAll()
				.requestMatchers("/admin").hasAnyRole("ADMIN")
				.anyRequest().permitAll()
			)

			.sessionManagement(sessionManagement -> sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.httpBasic(httpBasic -> httpBasic
				.disable())

			.csrf(csrf -> csrf
				.disable()
			)

			.formLogin(formLogin -> formLogin
				.disable()
			)

			.build();

	}

	//
	@Bean
	public AuthenticationManager authenticationManager() {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(jwtAuthenticationProvider);
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(principalDetailService);
		daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
		providers.add(daoAuthenticationProvider);
		log.info("AuthenticationManager 생성");
		return new ProviderManager(providers);
	}

}
