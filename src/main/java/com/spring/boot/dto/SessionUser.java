package com.spring.boot.dto;

import java.io.Serializable;

import com.spring.boot.model.BaseAuthUser;

import lombok.Getter;

//직렬화(프로그램 종료해도 메모리에 올려두고 재시작했을 때 추가적으로 로그인 할 필요 없게 함)
//세션값 담을 그릇(DTO)
@Getter
public class SessionUser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String email;
	private String picture;
	
	public SessionUser(BaseAuthUser user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.picture = user.getPicture();
	}
	
}
