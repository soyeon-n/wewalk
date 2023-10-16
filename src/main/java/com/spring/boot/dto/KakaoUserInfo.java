package com.spring.boot.dto;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{
	
	private String id;
	
	private Map<String, Object> kakaoAccount;

    public KakaoUserInfo(Map<String, Object> attributes, String id) {

        this.kakaoAccount = attributes;
        this.id = id;
    }

	@Override
	public String getProviderId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getProvider() {
		// TODO Auto-generated method stub
		return "kakao";
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return String.valueOf(kakaoAccount.get("email"));
	}

	@Override
	public String getName() {
		Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
		return String.valueOf(kakaoProfile.get("nickname"));
	}

	@Override
	public String getPicture() {
		Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
		return String.valueOf(kakaoProfile.get("profile_image_url"));
	}

}
