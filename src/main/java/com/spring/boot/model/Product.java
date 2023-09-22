package com.spring.boot.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
	
	
	@Id//primaryKey. 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer no;//상품고유번호
	
	@ManyToOne
	private String seller;//상품판매자? many to one 으로 연결 
	
	
	private String category;// 상품카테고리.꼭정해진카테고리테이블에서만.
	private String name;//상품명
	private String content;//상품설명
	private Integer price;//상품가격
	private LocalDateTime date;//상품등록일
	private Integer stock;//상품재고
	private String selling;//상품판매여부(판매중/판매완료)조건식으로 재고가0이면 이값을 F 로 바꾸던가헤야
	private String image;//대표이미지
	private String image1;//이미지1
	private String image2;//이미지2
	private String image3;//이미지3
	private String image4;//이미지4
	
	
	
	
	
	
	

}
