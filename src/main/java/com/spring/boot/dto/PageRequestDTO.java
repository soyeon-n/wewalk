package com.spring.boot.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO { 
    private int page;
    private int size;
    
    // 검색 처리를 위해 추가
    private String type;
    private String keyword;

    //기본 생성자
    //기본 페이지는 첫 페이지, 한 페이지에 보여주는 데이터 9개
    public PageRequestDTO(){
        this.page = 1;
        this.size = 9;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
    }
}