package com.spring.boot.model;

import lombok.RequiredArgsConstructor;

import lombok.Getter;

@Getter
@RequiredArgsConstructor
public enum BaseAuthRole {
	
	GUEST("ROLE_GUEST", "손님"), //회원가입 필요
	USER("ROLE_USER", "일반 사용자"); //이미 가입된 회원
	
	private final String key;
	private final String title;
	
}

