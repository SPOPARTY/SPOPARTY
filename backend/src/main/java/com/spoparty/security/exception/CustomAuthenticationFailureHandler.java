package com.spoparty.security.exception;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		if (exception instanceof BadCredentialsException) {
			log.error("CustomAuthenticationFailureHandler.BadCredentialsException : {}", exception.getMessage());
		} else if (exception instanceof UsernameNotFoundException) {
			log.error("CustomAuthenticationFailureHandler.UsernameNotFoundException : {}", exception.getMessage());
		} else {
			log.error("CustomAuthenticationFailureHandler.else : {}", exception.getMessage());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Authentication failed: " + exception.getMessage());
		}
	}
}

