package com.spring.boot.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paymoney")
    private Integer payMoney;
    
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "payDate")
    private Date payDate;
    
    @Column(name = "type")
    private String type;

}
