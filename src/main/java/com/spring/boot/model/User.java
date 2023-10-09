package com.spring.boot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="User")
public class User {
	//은별user. 일단 기본 회원테이블에서 셀렉가능한지 확인위한 회원테이블
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//name = "User_id",
	@Column(unique = true,nullable = false)
	private Long id;//id값은 하나, 상품은 여러개
	
	@Column(length = 100, nullable = false)
	private String email;
	private String username;
	private String password;
	private String name;
	private String jumin;
	private String address;
	private String tel;
	private String gender;
	private String picture;
	
	//product의 상품들과 연결될것이기떄문에 
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Product> productList;
	
	//빌더 없어도 되나?? 
	@Builder
	public User(String email,String username,String password,String name,String jumin, String address, String tel, String gender, String picture) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.name = name;
		this.jumin = jumin;
		this.address = address;
		this.tel = tel;
		this.gender = gender;
		this.picture = picture;
	}
	 

}
