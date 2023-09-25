package com.spring.boot.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.dao.GoodsRepository;
import com.spring.boot.dto.Goods;
import com.spring.boot.dto.GoodsForm;

@Service
public class GoodsService {
    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Transactional
    public void registerProduct(GoodsForm goodsForm) {
        Goods goods = new Goods();
        goods.setGdname(goodsForm.getGdname());
        goods.setContent(goodsForm.getContent());
        goods.setGdprice(goodsForm.getGdprice());
        // 필요한 다른 상품 속성 설정

        goodsRepository.save(goods); // JpaRepository의 save 메서드를 사용하여 저장
    }
}