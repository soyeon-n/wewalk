package com.spring.boot.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product{
	
	@Id//primaryKey. 
	@GeneratedValue(strategy = GenerationType.IDENTITY)//1씩 증가
	//@OneToMany//상품고유번호로 리뷰와 연결 안했음,,,
	private Long id;//상품고유번호
	//id 로 바꾸기 

	@ManyToOne
	@JoinColumn(nullable = false)
	private SiteUser user;//상품판매자? many to one 으로 연결 
	//bigint id 회원가입시 no 로 고유값아이디가 생긴다 

	private String category;// 상품카테고리.꼭정해진카테고리테이블에서만.
	@Column(nullable = false)
	private String pname;//상품명
	
	private String content;//상품설명
	
	private Integer price;//상품가격
	
	private LocalDateTime date;//상품등록일
	
	private Integer stock;//상품재고
	
	private String selling;//상품판매여부(판매중/판매완료)조건식으로 재고가0이면 이값을 F 로 바꾸던가헤야
	
	private String image;//대표이미지
	private String image1;//이미지1
	private String image2;//이미지2
	private String image3;//이미지3
	
	//review fk랑연결 
	@OneToMany(mappedBy = "product",cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<Review> reviewList;
	
	
	 
	@Builder
	public Product(String category, String pname,String content,Integer price,
			LocalDateTime date,Integer stock,String selling,
			String image,String image1,String image2,String image3,String image4) {
		this.category = category;
		this.pname = pname;
		this.content = content;
		this.price = price;
		this.date = date;
		this.stock = stock;
		this.selling = selling;
		this.image = image;
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;

				
	}
	

}
