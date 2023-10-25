package com.spring.boot.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
public class UserFiles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String saveFileName;
	private String originalFileName;
//	private String fileurl;
	private LocalDateTime uploadTime;
//	private LocalDateTime downloadTime;
	
	@Enumerated(EnumType.STRING)
	AttachmentType attachmentType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	SiteUser user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	SellerRequest sellerRequest;
	
	@Builder
	public UserFiles(String saveFileName, String originalFileName, 
			LocalDateTime uploadTime, AttachmentType attachmentType) {
        this.saveFileName = saveFileName;
        this.originalFileName = originalFileName;
//        this.fileurl = fileurl;
        this.uploadTime = uploadTime;
        this.attachmentType = attachmentType;
    }
	
}
