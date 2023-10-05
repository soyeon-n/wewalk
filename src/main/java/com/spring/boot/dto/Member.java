package com.spring.boot.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Getter
@Setter
@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
    private String userName;
    private String password;
    private String name;
    private String address;
    private String tel;
    private String photo;
    private String memberGrade;
    private String payMoney;
    private String point;

   
}