package com.spring.boot.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.web.multipart.MultipartFile;

public class GoodsForm {
	
	@NotEmpty(message = "상품명을 입력해주세요.")
    private String gdname; // 필드 이름 수정

    @NotEmpty(message = "상품 설명을 입력해주세요.")
    private String content;

    @NotNull(message = "가격을 입력해주세요.")
    @PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    private Integer gdprice; // 필드 이름 수정
    
    private List<MultipartFile> images;
    
    private String gdcategory;
    
    private List<String> gdtag;
    
    // 필드 이름과 어노테이션을 일치시켜야 합니다.

    public String getGdname() {
        return gdname;
    }

    public void setGdname(String gdname) {
        this.gdname = gdname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGdprice() {
        return gdprice;
    }

    public void setGdprice(Integer gdprice) {
        this.gdprice = gdprice;
    }

	public String getGdcategory() {
		return gdcategory;
	}

	public void setGdcategory(String gdcategory) {
		this.gdcategory = gdcategory;
	}

	public List<String> getGdtag() {
		return gdtag;
	}

	public void setGdtag(List<String> gdtag) {
		this.gdtag = gdtag;
	}

	public List<MultipartFile> getImages() {
		return images;
	}

	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}

	
}
