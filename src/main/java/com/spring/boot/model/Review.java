package com.spring.boot.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="Review")
public class Review {
	
	@Id//primaryKey. 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true,nullable = false)
	private Integer rno;//리뷰번호PK
	
	private String rUser;//리뷰작성자 = 여기에 현재로그인된 id 를 set 해야함 
	
	@ManyToOne
	@JoinColumn(name = "productNo",nullable = false)
	private Product product;//상품고유번호-fk productNo 가 아니라 product 테이블 자체?
	
	private String pname;//상품명--product 테이블에서가져오기
	
	private Integer star;//별점
	private LocalDateTime date;//리뷰등록일
	private String content;//리뷰내용
	private String photo;//상품사진
	
	

}
