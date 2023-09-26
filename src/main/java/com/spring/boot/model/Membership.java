package com.spring.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

//멤버십 테이블 생성용 클래스(추가적인 검토 필요)
@Data
@Entity
public class Membership {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gradeName;
    
    private String merit;
    
}
