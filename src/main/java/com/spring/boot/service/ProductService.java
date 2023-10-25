package com.spring.boot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.config.DataNotFoundException;

import com.spring.boot.dao.ProductRepository;
import com.spring.boot.dto.ProductForm;
import com.spring.boot.model.Product;
import com.spring.boot.model.SiteUser;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ProductService {
	
	@Autowired
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
	@Transactional
    public void registerProduct(ProductForm productForm, Product product, Long userId) {
       
		product.setPname(productForm.getPname());
		product.setContent(productForm.getContent());
		product.setPrice(productForm.getPrice());
		product.setCategory(productForm.getCategory());
		product.setStock(productForm.getStock());

        // 사용자 정보 설정 (글 작성시 product 테이블에 사용자 id 값도 넣기)
        SiteUser user = new SiteUser();
        user.setId(userId);
        product.setUser(user);

        // 상품 등록
        productRepository.save(product);
    }
	
	//상품 출력
	//public List<Product> getAllProducts() {
        // 모든 상품을 가져오는 로직
    //    return productRepository.findAll();
    //}
	
	//Sale 출력
	public int getSaleCount() {
	    List<Product> saleProduct = productRepository.findByStockGreaterThan(0);
	    return saleProduct.size();
	}
	
	//Soldout 출력
	public int getSoldoutCount() {
		List<Product> soldoutProduct = productRepository.findByStockEquals(0);
		return soldoutProduct.size();
	}
	
	public Page<Product> getProductsPaged(Long userId, int pageNum, int itemsPerPage) {
        Pageable pageable = PageRequest.of(pageNum - 1, itemsPerPage);
        // 사용자 ID를 사용하여 해당 사용자가 등록한 상품만 가져오도록 변경
        return productRepository.findByUserId(userId, pageable);
    }
	
	public Page<Product> getSaleProductsPaged(Long userId,int pageNum, int itemsPerPage) {
	    Pageable pageable = PageRequest.of(pageNum - 1, itemsPerPage);
	    return productRepository.findByUserIdAndStockGreaterThan(userId, 0, pageable);
	}

	public Page<Product> getSoldoutProductsPaged(Long userId, int pageNum, int itemsPerPage) {
	    Pageable pageable = PageRequest.of(pageNum - 1, itemsPerPage);
	    return productRepository.findByUserIdAndStockEquals(userId, 0, pageable);
	}

	public long getTotalItemCount(Long userId) {
	    return productRepository.countByUserId(userId);
	}
    
    public Product getProductById(Long id) {
        // 상품 ID를 사용하여 데이터베이스에서 상품 가져오기
        return productRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public void updateProduct(Long id, ProductForm productForm) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct != null) {
        	existingProduct.setPname(productForm.getPname());
        	existingProduct.setContent(productForm.getContent());
        	existingProduct.setPrice(productForm.getPrice());
        	existingProduct.setCategory(productForm.getCategory());
        	existingProduct.setStock(productForm.getStock());

            // 이미지 업로드 및 이미지 경로 가져오기
            List<String> imagePaths = uploadImages(productForm.getImages());

            // 이미지 경로 업데이트
            existingProduct.setImage(imagePaths.size() >= 1 ? imagePaths.get(0) : null);
            existingProduct.setImage1(imagePaths.size() >= 2 ? imagePaths.get(1) : null);
            existingProduct.setImage2(imagePaths.size() >= 3 ? imagePaths.get(2) : null);
            existingProduct.setImage3(imagePaths.size() >= 4 ? imagePaths.get(3) : null);

            productRepository.save(existingProduct);
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
	
	
	//희주님 꺼 
	public Product getProductById(Integer id) {
		Product product = productRepository.findById(id).get();
		return product;
	}
}
	
	
	

