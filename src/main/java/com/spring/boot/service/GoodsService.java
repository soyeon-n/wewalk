package com.spring.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.dao.GoodsRepository;
import com.spring.boot.dto.Goods;
import com.spring.boot.dto.GoodsForm;

@Service
public class GoodsService {
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Transactional
    public void registerProduct(GoodsForm goodsForm, Goods goods) {
        // Goods 엔티티에 나머지 정보 설정
        goods.setName(goodsForm.getName());
        goods.setContent(goodsForm.getContent());
        goods.setPrice(goodsForm.getPrice());
        goods.setCategory(goodsForm.getCategory());
        goods.setTag(goodsForm.getTag());
        goods.setStock(goodsForm.getStock());

        // 사용자 정보 설정 (여기서는 예시로 사용자 ID를 고정값으로 설정)
        //Long userId = "";
        //goods.setId(userId);

        // 상품 등록
        goodsRepository.save(goods);
    }
   
}    