package com.spring.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;
import com.spring.boot.config.ProductSpecification;
import com.spring.boot.config.SiteUserSpecification;
import com.spring.boot.dao.ProductRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.ItemDataForm;
import com.spring.boot.dto.OrderResultForm;
import com.spring.boot.dto.PageRequestDTO;
import com.spring.boot.dto.PageResultDTO;
import com.spring.boot.dto.ProductDTO;
import com.spring.boot.dto.SiteUserDTO;
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

	//검색기능을 포함한 상품 리스트(검색 방식에 따라 sorting 다르게 적용)
	public PageResultDTO<ProductDTO, Product> getSearchList(PageRequestDTO requestDTO, String sort) {
	    
		Pageable pageable;
		
		if(sort == "lowPrice") { //낮은 가격 순			
			pageable = requestDTO.getPageable(Sort.by("price").ascending());
		}else if(sort == "highPrice"){ //높은 가격 순		
			pageable = requestDTO.getPageable(Sort.by("price").descending());
		}else if(sort == "reviewCount") { //리뷰 많은 순
			pageable = requestDTO.getPageable(Sort.by("reviewList").descending());			
//		}else if(sort == "discount") { //할인율 높은 순
//			pageable = requestDTO.getPageable(Sort.by("discountRate").descending());			
		}else { //기본 값은 신상품순
			pageable = requestDTO.getPageable(Sort.by("date").descending());
		}
	    
	    Specification<Product> spec = ProductSpecification.isGreaterThanZero();
	    	  
	    spec = spec.and(ProductSpecification.hasKeyword(requestDTO.getKeyword()));

	    Page<Product> result = productRepository.findAll(spec, pageable);
	    
	    Function<Product, ProductDTO> fn = (entity -> entityToDto(entity));
	    
	    return new PageResultDTO<>(result, fn);
	}
	
	public Product dtoToEntity(ProductDTO dto){
		Product entity = Product.builder()
                .id(dto.getId())
                .category(dto.getCategory())
                .pname(dto.getPname())
                .content(dto.getContent())
                .price(dto.getPrice())
                .date(dto.getDate())
                .stock(dto.getStock())
                .selling(dto.getSelling())
                .image(dto.getImage())
                .image1(dto.getImage1())
                .image2(dto.getImage2())
                .image3(dto.getImage3())
                .build();
        return entity;
    }
	
	public ProductDTO entityToDto(Product entity){

		ProductDTO dto = ProductDTO.builder()
				.id(entity.getId())
                .category(entity.getCategory())
                .pname(entity.getPname())
                .content(entity.getContent())
                .price(entity.getPrice())
                .date(entity.getDate())
                .stock(entity.getStock())
                .selling(entity.getSelling())
                .image(entity.getImage())
                .image1(entity.getImage1())
                .image2(entity.getImage2())
                .image3(entity.getImage3())
                .build();

        return dto;
    }
}
	
	
	

