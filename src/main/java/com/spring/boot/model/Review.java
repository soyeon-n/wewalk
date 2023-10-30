package com.spring.boot.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Review")
public class Review {
	
	@Id//primaryKey. 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;//리뷰글번호PK 알아서 1씩 증가함 
	
	@ManyToOne
	private SiteUser user;//리뷰작성자 = 여기에 현재로그인된 id 를 set 해야함  
	 
	
	@ManyToOne
	private Product product;
	
	private String pname;//상품명--product 테이블에서가져오기
	
	private Integer star;//별점
	private Date date;//리뷰등록일
	private String title;//제목
	private String content;//리뷰내용
	
	//파일업로드 
	private String originalFileName;
	private String saveFileName;
	private String filePath;
	private String fileDir;//이거랑 + saveFileName 하면 fullpath 가나오므로 
	
	
	//private FileDTO fileDTO;
	
	
	//이미지 여러개일때 dto 생각해보기 .따로entity를 만드는것 같음
	//private List<UploadFile> imageFiles; 이미지가 여러개일시 UploadFile 객체를분리하고 이렇게 LIst 로 넣은..? 
	
	
	
	
	

}
