package com.spring.boot.model;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
@AllArgsConstructor의 유용성:

모든 필드를 초기화하며 객체를 생성해야 하는 경우에 매우 유용합니다.
객체를 불변(Immutable)으로 만들고 싶을 때 유용합니다. 
모든 필드를 final로 선언하고, @AllArgsConstructor로만 생성자를 제공하면, 객체 생성 후에 필드의 값을 변경할 수 없게 됩니다.
DTO(Data Transfer Object)나 VO(Value Object)와 같이 여러 필드 값을 한 번에 초기화해야 할 때 유용합니다.

@NoArgsConstructor의 유용성:

JPA 엔터티나 다른 ORM(Object-Relational Mapping) 프레임워크를 사용할 때는 기본 생성자가 필요합니다. 
이 때 @NoArgsConstructor를 사용하면 간편하게 기본 생성자를 제공할 수 있습니다.
객체를 생성한 후, setter 메서드를 통해 필드 값을 설정하려는 경우 유용합니다.
직렬화/역직렬화와 같은 작업을 수행할 때 기본 생성자가 필요한 경우도 있습니다.

또한, 명시적으로 모든 필드를 초기화하는 생성자를 제공하면서 동시에 기본 생성자도 제공하고 싶을 때는 
@AllArgsConstructor와 @NoArgsConstructor를 함께 사용할 수 있습니다.
 */

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SiteUser implements Serializable{

	//private static final long serialVersionUID = 517010009203139983L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role; //ADMIN, SELLER, USER, OAUTH
	
	private String email;

	//API 레벨에서 응답 요청하는 경우 JsonIgnore로 예외처리(보안)
	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@Column(unique = true)
	private String userName;
	
	private String provider; //어떤 OAuth인지(google, naver 등)
	
	@JsonIgnore
	private String providerId; // 해당 OAuth 의 key(id)
	
	private String name;

	//생성 일자(생성일시 추가하는 코드 추가 필요)
	@Column(nullable = false)
	private LocalDateTime createdDate;
	
	@Column(length = 12)
	private LocalDate birthDate;
	
	private String postcode;
	private String address;
	private String detailAddress;
	
	@Column(length = 20)
	private String tel;
	
	private String picture;
	
	//판매자 등록 여부(0 또는 1)
	//seller의 값이 1이고 role이 ADMIN이 아니면 SELLER role 부여
	@Column(columnDefinition = "TINYINT(1) default 0")
	private boolean seller;

	
	private String intro;
	
	//멤버 등급
	private String grade;

	
	//위워크페이 포인트(bigint로 들어가므로 -9,223,372,036,854,775,808부터 9,223,372,036,854,775,807까지의 정수값을 저장할 수 있음)
	//적립내역 테이블이 필요할 것 같음
	private Integer point;

	//충전해서쓰는페이머니 잔액
	private Integer paymoney;

	@ManyToOne(fetch = FetchType.LAZY)
    private Interest interest1;

    @ManyToOne(fetch = FetchType.LAZY)
    private Interest interest2;

    @ManyToOne(fetch = FetchType.LAZY)
    private Interest interest3;
    
    private LocalDateTime modifyDate;

    //처리 내역은 남겨둘 예정
    @OneToMany(mappedBy = "requestUser", fetch = FetchType.LAZY)
    private List<SellerRequest> sellerRequestList;
    
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isActivated;
    
    //은별
    //상품과 연결
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Product> productList;
    
    //내가쓴리뷰
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Review> reviewList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Question> questionList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Answer> answerList;
    

	//회원정보 수정(자동 반영)
	public SiteUser update(String userName, String picture) {
		this.userName = userName;
		this.picture = picture;
		
		return this;
	}
    
	
    //사용자 유형 식별(ADMIN / SELLER / USER)
  	public String getRoleKey() {
  		return this.role.getKey();
  	}
    
  	//set하기 위한 builder
  	@Builder
    public SiteUser(Long id, UserRole role, String email, String password, String userName, 
    		String provider, String providerId, String name, LocalDateTime createdDate, 
    		LocalDate birthDate, String postcode, String address, String detailAddress, boolean isActivated,
    		String tel, String picture, boolean seller, String intro, Integer paymoney, String grade,
    		Integer point, Interest interest1, Interest interest2, Interest interest3, LocalDateTime modifyDate) {
  		
  		this.id = id;
  		this.role = role;
  		this.email = email;
  		this.password = password;
        this.userName = userName;
        this.provider = provider;
        this.providerId = providerId;
        this.name = name;
        this.createdDate = createdDate;
        this.birthDate = birthDate;
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.isActivated = isActivated;
        this.tel = tel;
        this.picture = picture;
        this.seller = seller;
        this.intro = intro;
        this.paymoney = paymoney;
        this.point = point;
        this.grade = grade;
        this.interest1 = interest1;
        this.interest2 = interest2;
        this.interest3 = interest3;
        this.modifyDate = modifyDate;
    }
  	
  	@OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,
			fetch = FetchType.LAZY)
	private List<Address> adressList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Pay> payList;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Address> shippingList;
    
	
}

