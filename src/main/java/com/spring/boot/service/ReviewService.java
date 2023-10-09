package com.spring.boot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.boot.config.DataNotFoundException;
import com.spring.boot.dao.ReviewRepository;
import com.spring.boot.model.Product;
import com.spring.boot.model.Review;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ReviewService {
	
	
	private final ReviewRepository reviewRepository;
	
	
	//전체리뷰조회
		public Page<Review> getTotalReview(Pageable pageable){
			
			//최신글부터 desc해서 보기
			List<Sort.Order> sorts = new ArrayList<Sort.Order>();
			sorts.add(Sort.Order.desc("date"));
					
			
			//pageable 페이징 추가
			pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0 : pageable.getPageNumber()-1,
					pageable.getPageSize(),Sort.by(sorts));		
			
					
			return reviewRepository.findAll(pageable);
		}
		
		
		
		
		
	//새리뷰 등록
	//리뷰쓰기위해가지고들어갈것들
	public void createReview(Product product,Integer rUser,String pname,Integer star,
		String title, String content,String photo) {
		
		Review reviews = new Review();//객체생성
		
		reviews.setProduct(product);//리뷰한 상품번호 넣기
		//fk 인 동시에 insert 하려고해서 지금안들어가나 이게타입이 지금 int 가 아님 
		//reviews.setRNo(rNo);//리뷰글 번호 알아서 set 됨 
		reviews.setRUser(rUser);
		reviews.setPname(pname);
		reviews.setStar(star);
		reviews.setDate(LocalDateTime.now());
		reviews.setTitle(title);
		reviews.setContent(content);
		reviews.setPhoto(photo);
			
		reviewRepository.save(reviews);//insert
		
		//return reviews;//엄 return 헤야하나말아야하나
	}
	
	
	
	
	//한 id 로 리뷰셀렉하는거=내가 작성한 리뷰 보기
	public Page<Review> getReview(Integer rUser,Pageable pageable) {
		
		//내가쓴리뷰가 존재할수도있고 안할수도 있어서  optional 로 받음
		//optional 버려 
		
		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
		sorts.add(Sort.Order.desc("date"));
		
		pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0 : pageable.getPageNumber()-1,
				pageable.getPageSize(),Sort.by(sorts));
		
		//null검사 해야하는데 
		return reviewRepository.findByrUser(rUser, pageable);
		
		
		
	}
	
	
	
	
	//리뷰수정
	
	//리뷰삭제
	
	//리뷰추천수 저장
	
	//인기순으로 리뷰셀렉
	
	
	
	
	
	
	
	
	
	

}
