package com.spring.boot.dto;

import java.util.Date;

import com.spring.boot.model.SiteUser;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {

	private Long id;
	private SiteUser seller;//판매자
	private String category;//상품 카테고리
	private String pname;//상품명
	
	private String content;//상품설명
	
	private Integer price;//상품가격
	
	private Date date;//상품등록일
	
	private Integer stock;//상품재고
	
	private boolean selling;//상품판매여부(판매중/판매완료)조건식으로 재고가0이면 이값을 F 로 바꾸던가헤야
	
	private String image;//대표이미지
	private String image1;//이미지1
	private String image2;//이미지2
	private String image3;//이미지3
//	private List<Review> reviewList;//참조된 리뷰
	
	@Builder
	public ProductDTO(Long id, SiteUser seller, String category, String pname, 
			String content, Integer price, Date date, Integer stock, boolean selling,  
			String image, String image1, String image2, String image3) {
		
		this.id = id;
		this.seller = seller;
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
//		this.reviewList = reviewList;
		
	}
	
}
