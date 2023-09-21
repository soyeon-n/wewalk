package com.spring.boot.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class MyPage {
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private String id;
	
	@Id
	@Column(name = "EMAIL")
	private String email;
	
	@NotNull
	@Column(name = "USERNAME")
	private String nickname;
	
	@
	

}
