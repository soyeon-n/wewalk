package com.spring.boot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

//회원가입 검증용 Form
@Getter
@Setter
public class UserCreateForm {

	@NotEmpty(message = "이메일은 필수 항목입니다.")
	@Email(message = "이메일 형식으로 입력해주세요!")
	private String email;

	@Size(min = 6, max = 16, message = "사용자명은 6~16자 이내로 생성해주세요!")
	@NotEmpty(message = "사용자명은 필수 항목입니다.")
	private String userName;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", 
				message = "비밀번호는 최소 6자 이상이어야 하며, 영문자 대소문자와 특수문자를 모두 포함해야 합니다.")
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String password1;
	
	//비밀번호 일치 여부는 사용자 정의 유효성 검사기를 만들어야 할 것 같음
	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	private String password2;
	
	@NotEmpty(message = "이름은 필수 항목입니다.")
	private String name;
	
	@NotEmpty(message = "전화번호는 필수 항목입니다.")
	@Pattern(regexp = "^[0-9]*$", message = "전화번호는 숫자만 입력 가능합니다.")
	@Size(min = 10, max = 11, message = "전화번호는 10자리 또는 11자리여야 합니다.")
	private String tel;
	
	@NotEmpty(message = "주소는 필수 항목입니다.")
	private String address;
	
	@NotEmpty(message = "상세 주소는 필수 항목입니다.")
	private String detailAddress;

	@NotEmpty(message = "태어난 연도는 필수 항목입니다.")
	@Pattern(regexp = "^[0-9]*$", message = "태어난 연도는 숫자만 입력 가능합니다.")
	@Size(min = 4, max = 4, message = "태어난 연도는 4자리여야 합니다.")
	private String birth_year;
	
	@NotEmpty(message = "태어난 월은 필수 항목입니다.")
	@Pattern(regexp = "^(0?[1-9]|1[012])$", message = "태어난 월은 1부터 12 사이의 숫자만 입력 가능합니다.")
	private String birth_month;
	
	@NotEmpty(message = "태어난 일은 필수 항목입니다.")
	@Pattern(regexp = "^(0?[1-9]|1[0-9]|2[0-9]|3[0-1])$", message = "태어난 일은 숫자만 입력 가능합니다.")
	private String birth_day;
}