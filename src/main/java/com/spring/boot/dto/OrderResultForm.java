package com.spring.boot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResultForm {

	private String orderNo;
	private String productName;
	private String image;
	private String sellerId;
	private int count;
	private int price;
	
}
