package com.spring.boot.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(unique = true)
	private String userName;
	
	@Column(nullable = false)
	private String name;

	//생성 일자(생성일시 추가하는 코드 추가 필요)
	private LocalDateTime createdDate;
	
	@Column(length = 20, nullable = false)
	private String jumin;
	
	@Column(nullable = false)
	private String address;
	
	@Column(length = 20, nullable = false)
	private String tel;
	
	//성별 필요할까...?
	@Column(length = 1)
	private String gender;
	
	private String picture;
	
	//판매자 등록 여부(0 또는 1)
	@GeneratedValue()
	@Column(columnDefinition = "TINYINT(1) default 0")
	private boolean seller;
	
	//판매자 설명
	private String intro;

	//멤버십 등급(추후 연결되면 @OneToOne 추가 여부 검토 필요)
	private String grade;
	
	//페이금액(bigint로 들어가므로 
	//-9,223,372,036,854,775,808부터 9,223,372,036,854,775,807까지의 
	//정수값을 저장할 수 있음)
	private Long paymoney;
	
	//위워크페이 포인트
	private Long point;
	
	//lazy 설정을 통해 필요할 때만 꺼내오도록 하여 최적화
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest1_id")
	private int interest1;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest2_id")
	private int interest2;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest3_id")
	private int interest3;
	
}
