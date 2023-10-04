package com.spring.boot.service;

import java.util.Collections;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.BaseAuthUserRepository;
import com.spring.boot.dto.OAuthAttributes;
import com.spring.boot.dto.SessionUser;
import com.spring.boot.model.BaseAuthUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BaseCustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

	@Autowired
	private final BaseAuthUserRepository baseAuthUserRepository;
	
	@Autowired
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService = 
				new DefaultOAuth2UserService();
		
		OAuth2User oauth2User = oauthUserService.loadUser(userRequest);
		
		//소셜로그인 하는 곳이 어딘지 알려주는 값(카카오, 네이버, 구글...)
		String registrationId = 
				userRequest.getClientRegistration().getRegistrationId();
		
		//각 플랫폼에서 로그인하는 계정의 id값
		//구글 = sub, 네이버 = response, 카카오 = id
		String userNameAttributeName = 
				userRequest.getClientRegistration().getProviderDetails()
				.getUserInfoEndpoint().getUserNameAttributeName();
		
		System.out.println(userNameAttributeName);//sub, id, response
		
		OAuthAttributes attributes = 
				OAuthAttributes.of(registrationId, userNameAttributeName, oauth2User.getAttributes());
		
		System.out.println(attributes.getAttributes());
		
		BaseAuthUser authUser = saveOrUpdate(attributes);
		
		//로그인 한 계정의 attributes를 세션에 올림
		httpSession.setAttribute("user", new SessionUser(authUser));
		
		return new DefaultOAuth2User(Collections.singleton(
											new SimpleGrantedAuthority(authUser.getRoleKey())),
												attributes.getAttributes(), 
												attributes.getNameAttributeKey());
	} 
	
	//저장 또는 업데이트
	private BaseAuthUser saveOrUpdate(OAuthAttributes attributes) {
		BaseAuthUser authUser = baseAuthUserRepository
									.findByEmail(attributes.getEmail())
									.map(entity -> entity.update(attributes.getName(),
											attributes.getPicture()))
									.orElse(attributes.toEntity());
				
		return baseAuthUserRepository.save(authUser);
		
	}
	
}
