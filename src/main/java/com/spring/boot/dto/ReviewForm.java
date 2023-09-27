package com.spring.boot.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.spring.boot.model.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewForm {
	
	private Integer rno;//리뷰번호PK
	
	private String rUser;//리뷰작성자 = 여기에 현재로그인된 id 를 set 해야함
	
	private Product product;//상품고유번호-fk productNo 가 아니라 product 테이블 자체?
	
	private String pname;//상품명--product 테이블에서가져오기
	private Integer star;//별점
	private LocalDateTime date;//리뷰등록일
	
	@NotEmpty(message = "내용은필수항목입니다")
	//@size등제한은나중에@Size(max = 200)
	private String content;//리뷰내용
	private String photo;//상품사진
	

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
