package com.spring.boot.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PAY")
public class Pay {
	
	@OneToOne
	private User user;
	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "orderno", nullable = false)
    private Integer orderNo;

    @Column(name = "paymoney")
    private Integer payMoney;

    @Column(name = "point")
    private Integer point;
    
    //아임포트 결제시 필요한 컬럼
    @Column(nullable = false, length = 100)
	private String impUid;
	
	@Column(nullable = false, length = 100)
	private String merchantUid;
    

}
