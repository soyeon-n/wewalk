package com.spring.boot.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Goods {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pno")
    private Integer pno;
	
	@OneToOne
    @JoinColumn(name = "id")
	private User user;
    
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "content", length = 1000)
    private String content;
    
    @Column(name = "stock")
    private Integer stock;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "image1", length = 100)
    private String image1;

    @Column(name = "image2", length = 100)
    private String image2;

    @Column(name = "image3", length = 100)
    private String image3;

    @Column(name = "image4", length = 100)
    private String image4;

    @Column(name = "category", length = 30)
    private String category;

    @Column(name = "tag", length = 30)
    private String tag;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;
    
    @PrePersist
    protected void onCreate() {
    	date = new Date();
    }

	public void setTags(List<String> tags) {
		
	}

}
