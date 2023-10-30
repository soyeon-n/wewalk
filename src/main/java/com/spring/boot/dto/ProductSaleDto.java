package com.spring.boot.dto;

import lombok.Data;

@Data
public class ProductSaleDto {

	private Long productno;
    private Integer totalCount;

    public ProductSaleDto(Long productno, Integer totalCount) {
        this.productno = productno;
        this.totalCount = totalCount;
    }
	
}
