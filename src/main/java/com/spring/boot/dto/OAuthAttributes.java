package com.spring.boot.dto;

import java.util.Map;

import com.spring.boot.model.BaseAuthRole;
import com.spring.boot.model.BaseAuthUser;

import lombok.Builder;
import lombok.Getter;

//인증된 아이디의 데이터 담을 그릇(DTO)
@Getter
public class OAuthAttributes {

	//attributes로 맞춰줘야 함
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes, 
			String nameAttributeKey, String name, String email, String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
	}
	
	//구글, 네이버, 카카오 구분
	public static OAuthAttributes of(String registrationId, 
			String userNameAttributeName, Map<String, Object> attributes) {
		
		if(registrationId.equals("kakao")) {//id
			return ofKakao(userNameAttributeName, attributes);
		}else if(registrationId.equals("naver")) {//response
			return ofNaver("id", attributes);
		}else {
		//구글 실행
		//userNameAttributeName : sub
		return ofGoogle(userNameAttributeName, attributes);
		}		
	}
	
	//구글에서 데이터 곧장 받아옴
	private static OAuthAttributes ofGoogle(String userNameAttributeName, 
			Map<String, Object> attributes) {
		
		return OAuthAttributes.builder()
				.name((String)attributes.get("name"))
				.email((String)attributes.get("email"))
				.picture((String)attributes.get("picture"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
		
	}
	
	//카카오에서 데이터 곧장 받아옴
	private static OAuthAttributes ofKakao(String userNameAttributeName, 
			Map<String, Object> attributes) {
		
		//kakao_account에 사용자 정보(email)이 있음
		Map<String, Object> kakaoAccount = 
				(Map<String, Object>)attributes.get("kakao_account");
		
		Map<String, Object> kakaoProfile = 
				(Map<String, Object>)kakaoAccount.get("profile");
		
		return OAuthAttributes.builder()
				.name((String)kakaoProfile.get("nickname"))
				.email((String)kakaoAccount.get("email"))
				.picture((String)kakaoProfile.get("profile_image_url"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
		
	}
	
	//네이버에서 데이터 곧장 받아옴
	private static OAuthAttributes ofNaver(String userNameAttributeName, 
			Map<String, Object> attributes) {
		
		Map<String, Object> response = 
				(Map<String, Object>)attributes.get("response");
		
		return OAuthAttributes.builder()
				.name((String)response.get("name"))
				.email((String)response.get("email"))
				.picture((String)response.get("profile_image"))
				.attributes(response)
				.nameAttributeKey(userNameAttributeName)
				.build();
		
	}
	
	public BaseAuthUser toEntity() {
		
		return BaseAuthUser.builder()
				.name(name)
				.email(email)
				.picture(picture)
				.role(BaseAuthRole.GUEST)
				.build();
		
		
	}
	
}
