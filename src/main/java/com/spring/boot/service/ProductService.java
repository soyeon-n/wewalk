package com.spring.boot.service;

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
import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.ItemDataForm;
import com.spring.boot.dto.OrderResultForm;
import com.spring.boot.model.OrderList;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ProductService {
	
	private final ProductRepository productRepository;//서비스와 레포 연결
	private final UserRepository userRepository;
	
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
	
	
	//판매자 id 로 판매자의 상품 전체셀렉
	/*
	public Page<Product> getListsById(Pageable pageable){
		
		//최신글부터 전체셀렉
		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
		sorts.add(Sort.Order.desc("date"));
		
		//pageable 페이징 추가
		pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0: pageable.getPageNumber()-1,
				pageable.getPageSize(),Sort.by(sorts));
		
		//return productRepository.findByUserId(pageable);//뭘 넘겨줘야할지 
		//모르겠어 
		
	}
	*/
	//상품번호만으로 상품에대한 detail 조회하기 
	public Product getProductDetailByNo(Integer productNo){
		
		Optional<Product> product= productRepository.findById(productNo);
		
		
		if(product.isPresent()) {
			return product.get();
		}else {
			throw new DataNotFoundException("상세정보가 존재하지 않아요!");
		}
		
	}
	
	
	//소연언니
	//새상품 등록하기 
	
	//상품수정
	
	//상품삭제 
	//희주님 꺼 
	public Product getProductById(Long id) {
		Product product = productRepository.findById(id).get();
		return product;
	}
	
	public void updateProductStock(List<ItemDataForm> list) {
		
		for(ItemDataForm productData : list) {
			Long id = productData.getId();
			
			Product product = productRepository.findById(id).get();
			int newstock = product.getStock()-productData.getCount();
			product.setStock(newstock);
			
			productRepository.save(product);
		}
		
	}
	
	public List<OrderResultForm> getResultForm(List<OrderList> orderLists){
		List<OrderResultForm> lists = new ArrayList<OrderResultForm>();
		
		for (OrderList OrderListData : orderLists) {
			OrderResultForm orderResultForm = new OrderResultForm();
			SiteUser seller = userRepository.findById(OrderListData.getSellerid()).get();
			Product product = productRepository.findById(OrderListData.getProductno()).get();
			orderResultForm.setOrderNo(OrderListData.getOrderNo());
			orderResultForm.setProductName(product.getPname());
			orderResultForm.setImage(product.getImage());
			orderResultForm.setSellerId(seller.getUserName());
			orderResultForm.setCount(OrderListData.getCount());
			orderResultForm.setPrice(OrderListData.getPrice());
			
			lists.add(orderResultForm);
		}
		
		return lists;
	}

}
	
	
	

