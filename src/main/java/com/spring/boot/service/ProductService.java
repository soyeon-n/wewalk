package com.spring.boot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;

import com.spring.boot.dao.ProductRepository;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ProductService {
	
	private final ProductRepository productRepository;//서비스와 레포 연결
	
	//최신글부터 전체셀렉
	public Page<Product> getTotalLists(Pageable pageable){
		
		//최신글부터 전체셀렉
		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
		sorts.add(Sort.Order.desc("date"));
		
		//pageable 페이징 추가
		pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0: pageable.getPageNumber()-1,
				pageable.getPageSize(),Sort.by(sorts));
		
		return productRepository.findAll(pageable);//pageable 돌려주기
	}
	
	
	//판매자 id 로 판매자의 상품 전체리스트 셀렉	
	public List<Product> getListsById(SiteUser siteUser){
		long userid =  siteUser.getId();
		List<Product> product= productRepository.findByUserId(userid);
		
		
		return product;
	}
	
	//상품번호만으로 상품에대한 detail 조회하기 
	public Product getProductDetailByNo(long productNo){
		
		Optional<Product> product= productRepository.findById(productNo);
		
		
		if(product.isPresent()) {
			return product.get();
		}else {
			throw new DataNotFoundException("상세정보가 존재하지 않아요!");
		}
		
	}
	
	//현재조회한 상품을  팔고있는 판매자를 찾기 
	//public SiteUser getSeller()
	
	
	//소연언니
	//새상품 등록하기 
	
	//상품수정
	
	//상품삭제 
	//희주님 꺼 
	public Product getProductById(long id) {
		Product product = productRepository.findById(id).get();
		return product;
	}
}
	
	
	

