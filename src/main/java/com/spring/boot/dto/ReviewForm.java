package com.spring.boot.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
@Data
public class ReviewForm {//우리가 직접 넣는값만 form 검사 
	
	
	//private Integer rno;//리뷰번호PK
	
	private Integer rUser;//리뷰작성자 = 여기에 현재로그인된 id 를 set 해야함
	
	//private Integer productNo;//???
	
	private String pname;//상품명--product 테이블에서가져오기
	private Integer star;//별점
	//private LocalDateTime date;//리뷰등록일
	
	private String title;//제목
	@NotEmpty(message = "내용은필수항목입니다")
	//@size등제한은나중에@Size(max = 200)
	private String content;//리뷰내용
	
	//파일업로드 
	//모든내용이 form 거치는게 아니라 form 검사하는것만 
	
	//private String originalFilename;
	//private String saveFilename;//+ 시분초로 이름 겹치지 않게하기 
	//private String filepath;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
/*
@Size		문자 길이를 제한한다.
@NotNull	Null을 허용하지 않는다.
@NotEmpty	Null 또는 빈 문자열("")을 허용하지 않는다.
@Past		과거 날짜만 가능
@Future	미래 날짜만 가능
@FutureOrPresent미래 또는 오늘날짜만 가능
@Max		최대값
@Min		최소값
@Pattern	정규화표현식으로 검증
*/
