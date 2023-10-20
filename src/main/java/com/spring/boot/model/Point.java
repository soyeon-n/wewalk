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
public class Point {
	//포인트 적립및 사용내역
	@ManyToOne
	private SiteUser user;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "point")
    private Integer point;
    
	@Temporal(TemporalType.TIMESTAMP)
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
