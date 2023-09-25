package com.spring.boot.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.dao.GoodsRepository;
import com.spring.boot.dao.MyPageRepository;
import com.spring.boot.dto.Goods;
import com.spring.boot.dto.GoodsForm;
import com.spring.boot.dto.MyPage;

@Service
public class GoodsService {
	
    private final GoodsRepository goodsRepository;
    @Autowired
    private MyPageRepository myPageRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository, MyPageRepository myPageRepository) {
        this.goodsRepository = goodsRepository;
        this.myPageRepository = myPageRepository;
    }

    @Value("${upload.path}") // application.properties에서 업로드 경로 설정
    private String uploadPath;
    
    
    @Transactional
    public void registerProduct(GoodsForm goodsForm) {
    	
    	String gdimage1Path = saveImage(goodsForm.getImages().get(0));
    	String gdimage2Path = saveImage(goodsForm.getImages().get(1));
    	String gdimage3Path = saveImage(goodsForm.getImages().get(2));
    	String gdimage4Path = saveImage(goodsForm.getImages().get(3));
    	String gdimage5Path = saveImage(goodsForm.getImages().get(4));
    	
        Goods goods = new Goods();
        
        goods.setGdname(goodsForm.getGdname());
        goods.setContent(goodsForm.getContent());
        goods.setGdprice(goodsForm.getGdprice());
        goods.setGdimage1(gdimage1Path);
        goods.setGdimage2(gdimage2Path);
        goods.setGdimage3(gdimage3Path);
        goods.setGdimage4(gdimage4Path);
        goods.setGdimage5(gdimage5Path);
        
        // 필요한 다른 상품 속성 설정

        goodsRepository.save(goods); // JpaRepository의 save 메서드를 사용하여 저장
    }
    
    private String saveImage(MultipartFile image) {
        // 이미지를 서버에 저장하고 저장된 경로를 반환하는 코드 작성
    	
    	// 이미지를 저장할 디렉토리 경로 설정 (프로젝트 내에서 사용할 경로로 변경 필요)
        String uploadDirectory = "/path/to/upload/directory";

        // 업로드한 이미지 파일의 원래 파일 이름
        String originalFilename = image.getOriginalFilename();

        // 이미지 파일 이름에 UUID 추가하여 고유한 이름 생성
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

        try {
            // 이미지를 저장할 디렉토리가 없으면 생성
            Files.createDirectories(Paths.get(uploadDirectory));

            // 이미지 파일을 저장할 경로 설정
            Path imagePath = Paths.get(uploadDirectory, uniqueFileName);

            // 이미지 파일을 서버에 저장
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            // 저장된 이미지 파일의 경로를 반환
            return imagePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            // 이미지 저장 실패 시 예외 처리 필요
            return null;
        }
    }

    public void saveGoodsWithUserEmail(Goods goods, String email) {
        MyPage myPage = myPageRepository.findByEmail(email);
        goods.setMyPage(myPage); // Goods 엔티티에 MyPage를 설정
        goods.setEmail(email); // email 필드 설정
        goodsRepository.save(goods); // 상품 저장
    }
}