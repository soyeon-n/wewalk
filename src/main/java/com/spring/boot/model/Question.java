package com.spring.boot.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Question {//wewalk
	
	@Id//primaryKey. 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@ColumnDefault("1")
	private long id;//문의글 번호

	//private SiteUser author;//=author_ID
	@ManyToOne
	private SiteUser user;//문의자 >> fk 여부 고민중 그냥set 할까 
	 
	
	@ManyToOne
	private Product product;//문의할 상품번호
	
	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Answer> answerList;//질문에 대한 답변 list 들 
	
	private String title;//문의글제목
	
	private String content;//문의내용 
	
	private LocalDateTime date;
	
	private boolean done;//답변완료 여부 
	
	

}
