package com.spring.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring.boot.config.OrderListSpecifications;
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

	//판매량 상위 8개 제품 데이터 가져오기
	public List<Product> getTop8SellingProducts() {
	    // Specification을 사용하여 상위 판매 상품의 productno를 조회합니다.
	    Pageable top8 = PageRequest.of(0, 8);
	    
	    //판매량 상위 8개에 대한 데이터를 주문내역
	    List<OrderList> orderLists = orderListRepository.findAll(OrderListSpecifications.topSoldProducts(), top8).getContent();

	    List<Long> productnoList = orderLists.stream()
	                                .map(OrderList::getProductno) //OrderList 엔티티의 productno를 반복문으로 꺼냄
	                                .collect(Collectors.toList()); //꺼낸 데이터를 넣어줌

	    // 해당 productno로 Product 엔티티에서 상품 정보를 조회합니다.
	    return productRepository.findByIdIn(productnoList);
	}

	//페이징
	public List<OrderList> findOrderByUserId(Long userId) {
		
		SiteUser user = userRepository.findById(userId).get();
		
		return orderListRepository.findOrderByUser(user);
	}

	
	
}
