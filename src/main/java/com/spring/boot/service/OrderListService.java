package com.spring.boot.service;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.boot.dao.OrderListRepository;
import com.spring.boot.dao.ProductRepository;
import com.spring.boot.dao.UserRepository;
import com.spring.boot.dto.ItemDataForm;
import com.spring.boot.dto.PaymentDataForm;
import com.spring.boot.model.OrderList;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderListService {

	@Autowired
	private final OrderListRepository orderListRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	
	public List<OrderList> saveOrderHistory(PaymentDataForm paymentDataForm, SiteUser user) {
		
		List<ItemDataForm> list = paymentDataForm.getItemIds();
		List<OrderList> orderLists = new ArrayList<OrderList>();
		
		for (ItemDataForm productData : list) {
			
			OrderList orderlist = new OrderList();
			SiteUser seller = productRepository.findById(productData.getId()).get().getUser();
			
			orderlist.setOrderNo(paymentDataForm.getMerchant_uid());
			orderlist.setProductno(productData.getId());
			orderlist.setSellerid(seller.getId());
			orderlist.setUser(user);
			orderlist.setCount(productData.getCount());
			orderlist.setPrice(productData.getPrice()+3000);//택배비3000
			orderlist.setPayment(paymentDataForm.getPay_method());
			orderlist.setName(paymentDataForm.getBuyer_name());
			orderlist.setTel(paymentDataForm.getBuyer_tel());
			orderlist.setAddr(paymentDataForm.getBuyer_addr());
			orderlist.setZip(paymentDataForm.getBuyer_postcode());
			orderlist.setAddr_detail(paymentDataForm.getBuyer_addr_detail());
			orderlist.setRequest(paymentDataForm.getRequest());
			
			orderListRepository.save(orderlist);
			orderLists.add(orderlist);
		}
		
		return orderLists;
		
	}

	// 판매량 상위 8개 제품 데이터 가져오기
	public List<Product> getTop8SellingProducts() {
	    Pageable top8 = PageRequest.of(0, 8);
	    
	    // 판매량 상위 8개의 제품 및 판매량 정보 가져오기
	    Page<Object[]> topSellingProductsPage = orderListRepository.findTopSellingProducts(top8);
	    
	    // 상위 판매 제품 ID 리스트 추출
	    List<Long> productnoList = topSellingProductsPage.getContent().stream()
                .map(result -> (Long) result[0])
                .collect(Collectors.toList());


	    // 제품 ID에 해당하는 Product 엔터티 리스트 조회
	    List<Product> products = productRepository.findByIdIn(productnoList);
	    
	    // 판매량 순서대로 Product 엔터티 리스트 정렬
	    products.sort(Comparator.comparingInt(p -> productnoList.indexOf(p.getId())));
	    
	    return products;

	}

	//페이징
	public List<OrderList> findOrderByUserId(Long userId) {
		
		SiteUser user = userRepository.findById(userId).get();
		
		return orderListRepository.findOrderByUser(user);
	}

	
	
}
