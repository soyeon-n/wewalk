package com.spring.boot.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//회원가입 검증용 Form
@Getter
@Setter
public class UserCreateForm {

	@NotEmpty(message = "계정명은 필수 항목입니다.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])([a-zA-Z0-9]{6,16})$", 
			message = "계정명은 6~16자 이내의 영문 또는 영문 숫자 혼용이어야 합니다.")
	private String userName;
	
	@NotEmpty(message = "이메일은 필수 항목입니다.")
	@Email(message = "이메일 형식으로 입력해주세요!")
	private String email;

	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*+=])(?=\\S+$).{6,}$", 
			message = "비밀번호는 최소 6자 이상이어야 하며, 영문자 대소문자와 특수 문자, 그리고 숫자를 모두 포함해야 합니다.")
	@NotEmpty(message = "비밀번호는 필수 항목입니다.")
	private String password1;

	@NotEmpty(message = "비밀번호 확인은 필수 항목입니다.")
	private String password2;

	@NotEmpty(message = "이름은 필수 항목입니다.")
	private String name;

	@NotEmpty(message = "전화번호는 필수 항목입니다.")
	@Pattern(regexp = "^[0-9]*$", message = "전화번호는 숫자만 입력 가능합니다.")
	@Size(min = 10, max = 11, message = "전화번호는 10자리 또는 11자리여야 합니다.")
	private String tel;

	@NotEmpty(message = "우편번호는 필수 항목입니다.")
	private String postcode;
	
	@NotEmpty(message = "주소는 필수 항목입니다.")
	private String address;

	@NotEmpty(message = "상세 주소는 필수 항목입니다.")
	private String detailAddress;

	@NotEmpty(message = "태어난 연도는 필수 항목입니다.")
	@Pattern(regexp = "^[0-9]*$", message = "태어난 연도는 숫자만 입력 가능합니다.")
	@Size(min = 4, max = 4, message = "태어난 연도는 4자리여야 합니다.")
	private String birthYear;

	@NotEmpty(message = "태어난 월은 필수 항목입니다.")	
	@Pattern(regexp = "^(0?[1-9]|1[012])$", message = "태어난 월은 1부터 12 사이의 숫자만 입력 가능합니다.")
	private String birthMonth;

	@NotEmpty(message = "태어난 일은 필수 항목입니다.")
	@Pattern(regexp = "^(0?[1-9]|1[0-9]|2[0-9]|3[0-1])$", message = "태어난 일은 숫자만 입력 가능합니다.")
	private String birthDay;
	
//	@Builder
//	public UserCreateForm(String email, String userName, String name, 
//			String tel, String postcode, String address, String detailAddress, 
//			String birthYear, String birthMonth, String birthDay) {
//		
//		this.email = email;
//		this.userName = userName;
//		this.name = name;
//		this.tel = tel;
//		this.postcode = postcode;
//		this.address = address;
//		this.detailAddress = detailAddress;
//		this.birthYear = birthYear;
//		this.birthMonth = birthMonth;
//		this.birthDay = birthDay;
//		
//	}
}