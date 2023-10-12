package com.spring.boot.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.spring.boot.dao.UserRepository;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserRole;

//@Component
//public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//	@Autowired
//    private UserRepository userRepository; // 예시로 사용자 저장소를 가져옵니다.
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        
//        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
//        String email = token.getPrincipal().getAttribute("email"); // 이메일 얻기. 실제 attribute 이름은 서비스에 따라 달라질 수 있음.
//
//        // DB에서 사용자 정보 얻기
//        Optional<SiteUser> user = userRepository.findByEmail(email); // 예시. 실제 로직은 당신의 상황에 따라 다를 수 있음.
//
//        if (user != null && user.getRole() == BaseAuthRole.USER) { // 만약 사용자의 역할이 GUEST라면
//            getRedirectStrategy().sendRedirect(request, response, "/user/signup"); // 회원가입 페이지로 리디렉션
//        } else {
//            super.onAuthenticationSuccess(request, response, authentication); // 그렇지 않으면 기본 로직 실행
//        }
//    }
//	
//}
