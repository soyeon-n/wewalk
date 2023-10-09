package com.spring.boot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

//UserRole에 Seller라는 role을 추가하여 판매자 관리할 수 있게끔 세팅할 예정
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
	//이 부분 삭제 예정
	@Column(columnDefinition = "TINYINT(1) default 0")
	private boolean seller;
	
	//판매자 설명
	private String intro;

	//멤버십 등급(멤버십 테이블과 연결하려면 추후 혜택 등 디테일한 설정 필요)
	@ManyToOne
	private Membership membership;
	
	//위워크페이 포인트(bigint로 들어가므로 -9,223,372,036,854,775,808부터 9,223,372,036,854,775,807까지의 정수값을 저장할 수 있음)
	//적립내역 테이블이 필요할 것 같음
	private Long point;

	@ManyToOne(fetch = FetchType.LAZY)
    private Interest interest1;

    @ManyToOne(fetch = FetchType.LAZY)
    private Interest interest2;

    @ManyToOne(fetch = FetchType.LAZY)
    private Interest interest3;
    
    //해당 테이블도 삭제 예정
    @ManyToOne(fetch = FetchType.LAZY)
    private BaseAuthUser baseAuthUser;
    
    private LocalDateTime modifyDate;
	
    //로그인 비활성화 메소드 추가 예정(6개월)
    
    //사용자 유형 식별(Guest / User)
  	public String getRoleKey() {
  		return this.role.getKey();
  	}
    
}
