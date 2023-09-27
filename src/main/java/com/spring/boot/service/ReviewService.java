package com.spring.boot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.spring.boot.dao.ReviewRepository;
import com.spring.boot.model.Product;
import com.spring.boot.model.Review;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ReviewService {
	
	
	private final ReviewRepository reviewRepository;
	
	
	//전체리뷰조회
		public Page<Review> getReview(Pageable pageable){
			
			//최신글부터 desc해서 보기
			List<Sort.Order> sorts = new ArrayList<Sort.Order>();
			sorts.add(Sort.Order.desc("date"));
					
			
			//pageable 페이징 추가
			pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0 : pageable.getPageNumber()-1,
					pageable.getPageSize(),Sort.by(sorts));		
			
					
			return reviewRepository.findAll(pageable);
		}
		
		
	//새리뷰 등록
	//나중에 구매자만+ 로그인된사람만 가능하게 권한조치
	//리뷰쓰기위해가지고들어갈것들
	public Review createReview(Product productNo,String rUser,String pname,Integer star,LocalDateTime date,String content,String photo) {
		
		Review reviews = new Review();//객체생성
		
		reviews.setProduct(productNo);//리뷰한 상품번호 넣기
		reviews.setRUser(rUser);
		reviews.setPname(pname);
		reviews.setStar(star);
		reviews.setDate(LocalDateTime.now());
		reviews.setContent(content);
		reviews.setPhoto(photo);
			
		reviewRepository.save(reviews);//insert
		
		return reviews;//엄 return 헤야하나말아야하나
	}
	
	//한 id 로 리뷰셀렉하는거=내 리뷰 보기
	//public Review getReview(Integer userid )
	
	//리뷰삭제
	
	//리뷰추천수 저장
	
	//인기순으로 리뷰셀렉
	
	
	
	
	
	
	
	
	
	

}
