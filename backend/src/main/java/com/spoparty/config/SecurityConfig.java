package com.spoparty.config;

import com.spoparty.api.member.service.UserService;
import com.spoparty.common.auth.SsafyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private SsafyUserDetailService ssafyUserDetailService;
    
    @Autowired
    private UserService userService;
    
    // Password 인코딩 방식에 BCrypt 암호화 방식 사용
    @Autowired
    private PasswordEncoder passwordEncoder;

    // DAO 기반으로 Authentication Provider를 생성
    // BCrypt Password Encoder와 UserDetailService 구현체를 설정
//    @Bean
//    DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//        daoAuthenticationProvider.setUserDetailsService(this.ssafyUserDetailService);
//        return daoAuthenticationProvider;
//    }
//
//    // DAO 기반의 Authentication Provider가 적용되도록 설정
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(authenticationProvider());
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 하지않음
//                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService)) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
//                .authorizeRequests()
//                .antMatchers("/api/v1/users/me").authenticated()       //인증이 필요한 URL과 필요하지 않은 URL에 대하여 설정
//    	        	    .anyRequest().permitAll()
//                .and().cors();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/users/me").authenticated()
//                                .requestMatchers("/manager/").hasAnyRole("ADMIN", "MANAGER")    // 자동으로 ROLE_이 붙는다
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                );

//                .formLogin(formLogin ->
//                                formLogin
////                .usernameParameter("username")
////                .passwordParameter("password")
//                                        .loginPage("/loginForm")
//                                        .usernameParameter("username")
////                .failureUrl("/authentication/login?failed")
//                                        .loginProcessingUrl("/login")    // /login이라는 주소가 호출이 되며 시큐리티가 대신 진행.
//                                        .defaultSuccessUrl("/")
//                )
//
//                .oauth2Login(oauth2Login ->
//                        oauth2Login
//                                .loginPage("/loginForm")
//                                .userInfoEndpoint(userInfoEndpointConfig ->
//                                        userInfoEndpointConfig.userService(principalOAuth2UserService)
//                                )
//                );

        return http.build();
    }

}