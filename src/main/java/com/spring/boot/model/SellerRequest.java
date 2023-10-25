package com.spring.boot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SellerRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//신청자 id
	@ManyToOne(fetch = FetchType.LAZY)
	private SiteUser requestUser;
	
	//판매자 소개
	private String intro;
	
	//신청일시
	private LocalDateTime requestTime;
	
	//처리여부
	private boolean isProcessed;
	
	//처리일시
	private LocalDateTime processedTime;
	
	//반려사유
	private String denyReason;
	
	@OneToMany(mappedBy = "sellerRequest", fetch = FetchType.LAZY)
    private List<UserFiles> userFilesList;
	
	//set하기 위한 builder
  	@Builder
    public SellerRequest(SiteUser requestUser, String intro, 
    		LocalDateTime requestTime, boolean isProcessed, 
    		LocalDateTime processedTime) {
  		
  		this.requestUser = requestUser;
  		this.intro = intro;
  		this.requestTime = requestTime;
        this.isProcessed = isProcessed;
        this.processedTime = processedTime;
        
    }
	
}
