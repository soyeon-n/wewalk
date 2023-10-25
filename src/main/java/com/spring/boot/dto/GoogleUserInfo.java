package com.spring.boot.dto;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{
	
	private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes) {

        this.attributes = attributes;
    }

	@Override
	public String getProviderId() {
		// TODO Auto-generated method stub
		return String.valueOf(attributes.get("sub"));
	}

	@Override
	public String getProvider() {
		// TODO Auto-generated method stub
		return "google";
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return String.valueOf(attributes.get("email"));
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return String.valueOf(attributes.get("name"));
	}

	@Override
	public String getPicture() {
		// TODO Auto-generated method stub
		return String.valueOf(attributes.get("picture"));
	}

}
