package com.spring.boot.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// deactivated 돼있을 때 해당 메커니즘이 동작을 안함
		if (authException instanceof UsernameNotFoundException && authException.getMessage().contains("User is deactivated")) {
			System.out.println("");
			String userName = request.getParameter("userName"); // 로그인 폼에서의 입력 필드 이름을 기준으로 함
			response.sendRedirect("/auth/reactivateUser?userName=" + userName);
        } else {
            response.sendRedirect("/login?error");
        }
	}

}
