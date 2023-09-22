package com.spring.boot.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
	
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String email;

}
