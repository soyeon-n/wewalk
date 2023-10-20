package com.spring.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.boot.dao.OrderListRepository;
import com.spring.boot.dao.ProductRepository;
import com.spring.boot.dto.ItemDataForm;
import com.spring.boot.dto.PaymentDataForm;
import com.spring.boot.model.OrderList;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderListService {

	private final OrderListRepository orderListRepository;
	private final ProductRepository productRepository;
	
	public void saveOrderHistory(PaymentDataForm paymentDataForm, SiteUser user) {
		
		List<ItemDataForm> list = paymentDataForm.getItemIds();
		
		for (ItemDataForm productData : list) {
			
			OrderList orderlist = new OrderList();
			Long sellerid = productRepository.findById(productData.getId()).get().getId();
			
			orderlist.setOrderNo(paymentDataForm.getMerchant_uid());
			orderlist.setProductno(productData.getId());
			orderlist.setSellerid(sellerid);
			orderlist.setUserid(user.getId());
			orderlist.setCount(productData.getCount());
			orderlist.setPrice(productData.getPrice());
			orderlist.setPayment(paymentDataForm.getPay_method());
			orderlist.setName(paymentDataForm.getBuyer_name());
			orderlist.setTel(paymentDataForm.getBuyer_tel());
			orderlist.setAddr(paymentDataForm.getBuyer_addr());
			orderlist.setZip(paymentDataForm.getBuyer_postcode());
			orderlist.setAddr_detail(paymentDataForm.getBuyer_addr_detail());
			orderlist.setRequest(paymentDataForm.getRequest());
			
			orderListRepository.save(orderlist);
			
		}
		
		
	}
	
	
	
	
}
