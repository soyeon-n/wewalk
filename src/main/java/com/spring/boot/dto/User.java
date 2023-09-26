package com.spring.boot.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToMany(mappedBy = "user")
	private List<Shipping> shippingList;
	
	@OneToMany(mappedBy = "user")
    private List<Goods> goodsList;
	
	@Column(name = "EMAIL")
	private String email;
	
	@NotNull
	@Column(name = "USERNAME")
	private String nickname;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TEL")
	private String tel;
	
	@Column(name = "PHOTO")
	private String photo;
	
	@Column(name = "MEMBERGRADE")
	private String membergrade;
	
	@Column(name = "PAYMONEY")
	private String paymoney;
	
	@Column(name = "POINT")
	private String point;

}
