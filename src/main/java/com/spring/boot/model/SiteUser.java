package com.spring.boot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

import com.spring.boot.dto.Goods;
import com.spring.boot.dto.Pay;
import com.spring.boot.dto.Shipping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
	@Column(unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(unique = true)
	private String userName;
	
	@Column(nullable = false)
	private String name;

	//생성 일자(생성일시 추가하는 코드 추가 필요)
	@Column(nullable = false)
	private LocalDateTime createdDate;
	
	@Column(length = 12, nullable = false)
	private LocalDate birthDate;
	
	@Column(nullable = false)
	private String postcode;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String detailAddress;
	
	@Column(length = 20, nullable = false)
	private String tel;
	
	private String picture;
	
	//판매자 등록 여부(0 또는 1)
	@Column(columnDefinition = "TINYINT(1) default 0")
	private boolean seller;
	
	//판매자 설명
	private String intro;

	//멤버 등급
	private String grade;
	
	//위워크페이 포인트(bigint로 들어가므로 -9,223,372,036,854,775,808부터 9,223,372,036,854,775,807까지의 정수값을 저장할 수 있음)
	//적립내역 테이블이 필요할 것 같음
	private Long point;
	
	@ColumnDefault("0")
	private Integer paymoney;

	@ManyToOne(fetch = FetchType.LAZY)
    private Interest interest1;

    @ManyToOne(fetch = FetchType.LAZY)
    private Interest interest2;

    @ManyToOne(fetch = FetchType.LAZY)
    private Interest interest3;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private BaseAuthUser baseAuthUser;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Pay> payList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Shipping> shippingList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Goods> productList;
	
}