package com.spring.boot.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.GoogleUserInfo;
import com.spring.boot.dto.KakaoUserInfo;
import com.spring.boot.dto.NaverUserInfo;
import com.spring.boot.dto.OAuth2UserInfo;
import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.model.SiteUser;
import com.spring.boot.model.UserRole;
import com.spring.boot.util.PasswordGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuthSevice extends DefaultOAuth2UserService{

	private final UserRepository userRepository;
	
	//BCrypt해시 함수 호출
	private final PasswordEncoder passwordEncoder;
	
	//구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //"registraionId"로 어떤 OAuth 로 로그인 했는지 확인 가능(google,naver등)
        System.out.println("getClientRegistration: "+ userRequest.getClientRegistration());
        System.out.println("getAccessToken: "+userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttributes: "+ super.loadUser(userRequest).getAttributes());
        //구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 -> code를 리턴(OAuth-Clien라이브러리가 받아줌) -> code를 통해서 AcssToken요청(access토큰 받음)
        // => "userRequest"가 감고 있는 정보
        //회원 프로필을 받아야하는데 여기서 사용되는것이 "loadUser" 함수이다 -> 구글 로 부터 회원 프로필을 받을수 있다.


        /**
         *  OAuth 로그인 회원 가입
         */
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>)oAuth2User.getAttributes().get("response"));
        }
        else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoUserInfo((Map<String, Object>)oAuth2User.getAttributes().get("kakao_account"),
                                String.valueOf(oAuth2User.getAttributes().get("id")));
        }
        else{
            System.out.println("지원하지 않는 로그인 서비스 입니다.");
        }

        // 랜덤 비밀번호 생성
        String randomPassword = PasswordGenerator.generateRandomPassword();

        // 비밀번호 해싱
        String hashedPassword = passwordEncoder.encode(randomPassword);
        
        String provider = oAuth2UserInfo.getProvider(); //google , naver, facebook etc
        String providerId = oAuth2UserInfo.getProviderId();
        String userName = provider + "_" + oAuth2UserInfo.getName();
        String password = hashedPassword;
        String email = oAuth2UserInfo.getEmail();
        UserRole role = UserRole.OAUTH;
        String picture = oAuth2UserInfo.getPicture();

        //Email 주소가 겹치거나 userName이 변경될 경우 문제가 발생하므로 providerId로 검색
        Optional<SiteUser> userEntity = userRepository.findByProviderId(providerId);
        
        if(!userEntity.isPresent()) {
        	
        	LocalDateTime createdTime = LocalDateTime.now();
        	
        	System.out.println("처음 접속한 소셜 로그인 회원");
        	
        	SiteUser siteUser = SiteUser.builder()
			        				.userName(userName)
			        				.password(password)
			        				.email(email)
			        				.role(role)
			        				.provider(provider)
			        				.providerId(providerId)
			        				.createdDate(createdTime)
			        				.picture(picture)
			        				.isActivated(true)
			        				.build();
        	
        	userRepository.save(siteUser);
        	siteUser.setPassword(null);
        	System.out.println(siteUser);
        	
        	return new PrincipalDetails(siteUser, oAuth2User.getAttributes());
        	
        }else if(userEntity.isPresent() == true && userEntity.get().isActivated() == false){//비활성화 되어있을 시 로그인 안되게 처리                	
        	System.out.println("비활성화된 계정입니다");
        	throw new DisabledException("비활성화된 계정입니다");
        }else {
        	
        	SiteUser existingUser = userEntity.get();  	        	
        	
        	if(existingUser.getRoleKey().equals("OAUTH")) {
        		System.out.println("회원가입이 필요한 회원");
        	}else {        		
        		System.out.println("기존 소셜 로그인 회원");
        	}        	
        	
        	existingUser.setUserName(userName);
        	existingUser.setPicture(picture);
        	
        	userRepository.save(existingUser);
        	
        	System.out.println(existingUser);
        	existingUser.setPassword(null);
        	
        	return new PrincipalDetails(existingUser, oAuth2User.getAttributes());
        }

    }
	
}
