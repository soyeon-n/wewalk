package com.spring.boot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	
	//Sale 출력
	public int getSaleCount() {
	    List<Goods> saleGoods = goodsRepository.findByStockGreaterThan(0);
	    return saleGoods.size();
	}
	
	//Soldout 출력
	public int getSoldoutCount() {
		List<Goods> soldoutGoods = goodsRepository.findByStockEquals(0);
		return soldoutGoods.size();
	}
	
	public Page<Goods> getProductsPaged(Long userId, int pageNum, int itemsPerPage) {
        Pageable pageable = PageRequest.of(pageNum - 1, itemsPerPage);
        // 사용자 ID를 사용하여 해당 사용자가 등록한 상품만 가져오도록 변경
        return goodsRepository.findByUserId(userId, pageable);
    }
	
	public Page<Goods> getSaleProductsPaged(Long userId,int pageNum, int itemsPerPage) {
	    Pageable pageable = PageRequest.of(pageNum - 1, itemsPerPage);
	    return goodsRepository.findByUserIdAndStockGreaterThan(userId, 0, pageable);
	}

	public Page<Goods> getSoldoutProductsPaged(Long userId, int pageNum, int itemsPerPage) {
	    Pageable pageable = PageRequest.of(pageNum - 1, itemsPerPage);
	    return goodsRepository.findByUserIdAndStockEquals(userId, 0, pageable);
	}

    public long getTotalItemCount() {
        return goodsRepository.count();
    }
    
    public Goods getGoodsById(int pno) {
        // 상품 ID를 사용하여 데이터베이스에서 상품 가져오기
        return goodsRepository.findById(pno).orElse(null);
    }
    
    @Transactional
    public void updateGoods(int pno, GoodsForm goodsForm) {
        Goods existingGoods = goodsRepository.findById(pno).orElse(null);

        if (existingGoods != null) {
            existingGoods.setName(goodsForm.getName());
            existingGoods.setContent(goodsForm.getContent());
            existingGoods.setPrice(goodsForm.getPrice());
            existingGoods.setCategory(goodsForm.getCategory());
            existingGoods.setStock(goodsForm.getStock());

            // 이미지 업로드 및 이미지 경로 가져오기
            List<String> imagePaths = uploadImages(goodsForm.getImages());

            // 이미지 경로 업데이트
            existingGoods.setImage(imagePaths.size() >= 1 ? imagePaths.get(0) : null);
            existingGoods.setImage1(imagePaths.size() >= 2 ? imagePaths.get(1) : null);
            existingGoods.setImage2(imagePaths.size() >= 3 ? imagePaths.get(2) : null);
            existingGoods.setImage3(imagePaths.size() >= 4 ? imagePaths.get(3) : null);

            goodsRepository.save(existingGoods);
        }
    }

    private List<String> uploadImages(List<MultipartFile> images) {
        List<String> imagePaths = new ArrayList<>();
        
        for (MultipartFile image : images) {
            if (image != null && !image.isEmpty()) {
                String imagePath = saveImage(image);
                if (imagePath != null) {
                    imagePaths.add(imagePath);
                }
            }
        }

        return imagePaths;
    }
    
    public String saveImage(MultipartFile image) {
        String uploadDirectory = "src/main/resources/static/product";

        String originalFilename = image.getOriginalFilename();

        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

        try {
            Files.createDirectories(Paths.get(uploadDirectory));
            Files.copy(image.getInputStream(), Paths.get(uploadDirectory, uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
            return uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
   
}    