package com.spring.boot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

	@Size(min = 3, max = 25, message = "사용자명은 3~25자 이내로 생성해주세요!")
	@NotEmpty(message = "사용자명은 필수 항목입니다.")
	private String userName;
	
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String password1;
	
	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	private String password2;

	@NotEmpty(message = "이메일은 필수 항목입니다.")
	@Email(message = "이메일 형식으로 입력해주세요!")
	private String email;
}