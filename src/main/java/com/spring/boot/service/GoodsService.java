package com.spring.boot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.dao.GoodsRepository;
import com.spring.boot.dto.Goods;
import com.spring.boot.dto.GoodsForm;
import com.spring.boot.model.SiteUser;

@Service
public class GoodsService {
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Transactional
    public void registerProduct(GoodsForm goodsForm, Goods goods, Long userId) {
        // Goods 엔티티에 나머지 정보 설정
        goods.setName(goodsForm.getName());
        goods.setContent(goodsForm.getContent());
        goods.setPrice(goodsForm.getPrice());
        goods.setCategory(goodsForm.getCategory());
        goods.setTag(goodsForm.getTag());
        goods.setStock(goodsForm.getStock());

        // 사용자 정보 설정 (글 작성시 product 테이블에 사용자 id 값도 넣기)
        SiteUser user = new SiteUser();
        user.setId(userId);
        goods.setUser(user);

        // 상품 등록
        goodsRepository.save(goods);
    }
	
	//상품 출력
	public List<Goods> getAllProducts() {
        // 모든 상품을 가져오는 로직
        return goodsRepository.findAll();
    }
   
}    