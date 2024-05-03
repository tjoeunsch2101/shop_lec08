package com.tjoeun.shop.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		if("XMLHTTPRequest".equals(request.getHeader("x-requested-with"))){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증에 실패했습니다 (UNAUTHORIZED)");
		}else {
			response.sendRedirect("/member/signin");
		}

	}

}
