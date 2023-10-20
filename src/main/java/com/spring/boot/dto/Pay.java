package com.spring.boot.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.spring.boot.model.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PAY")
public class Pay {
	
	@ManyToOne
	private SiteUser user;
	
	@Id
	@Column(name = "orderno")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderNo;

    @Column(name = "paymoney")
    private Integer payMoney;
    
    @Column(name = "payDate")
    private Date payDate;
    
    @Column(name = "type")
    private String type;
    
    /* 아임포트 결제시 필요한 컬럼
    @Column(nullable = false, length = 100)
	private String impUid;
	
	@Column(nullable = false, length = 100)
	private String merchantUid;
	*/

    

}
