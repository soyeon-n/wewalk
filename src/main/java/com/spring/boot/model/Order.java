package com.spring.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderno;
	
	private int productno;
	
	private int sellerid;
	
	private int buyerid;
	
	private int count;
	
	private int price;
	
	private String payment;
	
	private String name;
	
	private String tel;
	
	private String addr;
	
	private String zip;
	
	private String addr_detail;
	
	
}
