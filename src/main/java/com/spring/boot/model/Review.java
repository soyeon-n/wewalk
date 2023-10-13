package com.spring.boot.model;

import java.time.LocalDateTime;
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
	//@ColumnDefault("1")
	//@Column(unique = true,nullable = false)//짜피 id 니까.. ? 
	private Integer id;//리뷰글번호PK 알아서 1씩 증가함 
	//rno 말고 id 로 고유번호를 바꿨더니 알아서 1씩 증가 
	
	private Integer rUser;//리뷰작성자 = 여기에 현재로그인된 id 를 set 해야함 
	//userid 로 바꿔야 
	//이것도 회원번호랑 ,, ,,, fk 연결해야하나 ? 
	
	
	@ManyToOne
	@JoinColumn(name = "productNo",nullable = false)
	private Product product;//상품고유번호-fk productNo 가 아니라 product 테이블 자체?
	//이게왜 int 가 아니라 product 타입이어서 새로 insert 가 안되냐는 ...말이야 fk 인데 
	//가져오는 fk 인데 insert 도 해야하는데 .>>???????? 이거자체가 productNo 를 의미하는거
	private String pname;//상품명--product 테이블에서가져오기
	
	private Integer star;//별점
	private LocalDateTime date;//리뷰등록일
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
