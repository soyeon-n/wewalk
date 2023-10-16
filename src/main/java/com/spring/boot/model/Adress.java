package com.spring.boot.model;

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
public class Adress {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private String type;
	
	private String receivername;
	
	private String phone;
	
	private String address01;
	
	private String address02;
	
	private String zipcode;
	
	@ManyToOne
	private SiteUser user;
	
}
