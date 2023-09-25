package com.spring.boot.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "goods")
public class Goods {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gdno")
    private Integer gdno;
	
	@ManyToOne // 다대일 관계 설정
    @JoinColumn(name = "user_email", referencedColumnName = "email")
	private MyPage myPage; // MyPage 엔티티와의 관계 설정
    
    @Column(name = "user_email", insertable = false, updatable = false) // 사용자 이메일 필드
    private String email;

    @Column(name = "gdname", length = 50)
    private String gdname;

    @Column(name = "gdprice")
    private Integer gdprice;

    @Column(name = "gdcondition", length = 20)
    private String gdcondition;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "gdimage1", length = 100)
    private String gdimage1;

    @Column(name = "gdimage2", length = 100)
    private String gdimage2;

    @Column(name = "gdimage3", length = 100)
    private String gdimage3;

    @Column(name = "gdimage4", length = 100)
    private String gdimage4;

    @Column(name = "gdimage5", length = 100)
    private String gdimage5;

    @Column(name = "gdcategory", length = 30)
    private String gdcategory;

    @Column(name = "gdtag", length = 30)
    private String gdtag;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "writeday")
    private Date writeday;
    
    @PrePersist
    protected void onCreate() {
    	writeday = new Date();
    }

    @Column(name = "inventory")
    private Integer inventory;

	public void setDescription(String description) {
		// TODO Auto-generated method stub
		
	}

}
