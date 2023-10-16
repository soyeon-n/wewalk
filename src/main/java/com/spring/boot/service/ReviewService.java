package com.spring.boot.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.multipart.MultipartFile;

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
	
	
	//productNo 로 상품의모든리뷰 조회하기 
	public Page<Review> getPnoReview (Pageable pageable,Product productNo) {
		
		
		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
		sorts.add(Sort.Order.desc("date"));
		
		pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0 : pageable.getPageNumber()-1,
				pageable.getPageSize(),Sort.by(sorts));


		return reviewRepository.findByProduct(productNo, pageable);
		
		
	}
	





	//새리뷰 작성 저장 
	//리뷰쓰기위해가지고들어갈것들
	public void createReview(Product product,Integer rUser,String pname,Integer star,
			String title, String content,MultipartFile multipartFile) throws IOException{

		Review reviews = new Review();//객체생성



		//uuid~ filemanager 코딩  
		String rootPath = System.getProperty("user.dir");

		String fileDir = rootPath + "/files/";//내가만든파일폴더이름


		//날짜저장
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter =
				DateTimeFormatter.ofPattern("yyyyMMdd");
		String currentDate = now.format(dateTimeFormatter);


		//새로 만들기 
		File file = new File(fileDir);		

		if(!file.exists()) {
			boolean wasSuccessful = file.mkdirs();

			// 디렉터리 생성에 실패했을 경우
			if(!wasSuccessful)
				System.out.println("file: was not successful");
		}  


		//origin 파일 네임 .확장자를 포함한 파일이름 추출 
		String originalFileName = multipartFile.getOriginalFilename();

		//확장자 붙이기 
		int pos = originalFileName.lastIndexOf(".");

		//origin 파일 네임 이거 지금 오류나는중
		String extractExt=originalFileName.substring(pos + 1);//확장자명

		//저장할 파일 네임
		String saveFileName = currentDate +System.nanoTime() +  "."+ extractExt;//확장자 추출하여 db 저장네임만들기


		//fullpath
		String fullPath = fileDir + saveFileName;

		//실제로 파일을 저장하는 부분 -> 파일경로 + storeFilename 에 저장
		multipartFile.transferTo(new File(fullPath));



		reviews.setOriginalFileName(originalFileName);
		reviews.setSaveFileName(saveFileName);
		reviews.setFilePath(fullPath);
		reviews.setFileDir(fileDir);



		reviews.setProduct(product);//리뷰한 상품번호 넣기
		//fk 인 동시에 insert 하려고해서 지금안들어가나 이게타입이 지금 int 가 아님 
		//reviews.setRNo(rNo);//리뷰글 번호 알아서 set 됨 
		reviews.setRUser(rUser);
		reviews.setPname(pname);
		reviews.setStar(star);
		reviews.setDate(LocalDateTime.now());
		reviews.setTitle(title);
		reviews.setContent(content);



		//db에 저장하는부분
		reviewRepository.save(reviews);//insert

		//return reviews;//엄 return 헤야하나말아야하나
	}




	//한 작성자 id 로 리뷰셀렉하는거=내가 작성한 리뷰 모두 보기
	public Page<Review> getReview(Integer rUser,Pageable pageable) {

		//내가쓴리뷰가 존재할수도있고 안할수도 있어서  optional 로 받음
		//optional 버려 아직 못씀 ,,null 예외처리 필요 

		List<Sort.Order> sorts = new ArrayList<Sort.Order>();
		sorts.add(Sort.Order.desc("date"));

		pageable = PageRequest.of(pageable.getPageNumber()<=0 ? 0 : pageable.getPageNumber()-1,
				pageable.getPageSize(),Sort.by(sorts));


		return reviewRepository.findByrUser(rUser, pageable);



	}



	//하나의 리뷰=id 가져오기 
	public Review getOneReview(Integer id) {
		Optional<Review> opreview = reviewRepository.findById(id);

		if(opreview.isPresent()) {
			return opreview.get();
		}else {
			throw new DataNotFoundException("리뷰가 없습니다.");
		}
	}

	//리뷰수정해서 다시 set 하여 넣기
	//Product product,Integer rUser,String pname,Integer star,
	//String title, String content,MultipartFile multipartFile
	public void modifyReview(Review review,Integer star,
			String title, String content,MultipartFile multipartFile) throws IOException{

		//기존의 한 review 를 가져옴 

		//파일재업로드기능은아직임 

		//uuid~ filemanager 코딩  
		//기존 file 이 있으면 이 코딩을 안하는 조건을 넣을수 있을까 ? 
		if(multipartFile!=null) {

			String rootPath = System.getProperty("user.dir");

			String fileDir = rootPath + "/files/";//내가만든파일폴더이름


			//날짜저장
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateTimeFormatter =
					DateTimeFormatter.ofPattern("yyyyMMdd");
			String currentDate = now.format(dateTimeFormatter);


			//새로 만들기 
			File file = new File(fileDir);		

			if(!file.exists()) {
				boolean wasSuccessful = file.mkdirs();

				// 디렉터리 생성에 실패했을 경우
				if(!wasSuccessful)
					System.out.println("file: was not successful");
			}  


			//origin 파일 네임 .확장자를 포함한 파일이름 추출 
			String originalFileName = multipartFile.getOriginalFilename();

			//확장자 붙이기 
			int pos = originalFileName.lastIndexOf(".");

			//origin 파일 네임 이거 지금 오류나는중
			String extractExt=originalFileName.substring(pos + 1);//확장자명

			//저장할 파일 네임
			String saveFileName = currentDate +System.nanoTime() +  "."+ extractExt;//확장자 추출하여 db 저장네임만들기


			//fullpath
			String fullPath = fileDir + saveFileName;

			//실제로 파일을 저장하는 부분 -> 파일경로 + storeFilename 에 저장
			multipartFile.transferTo(new File(fullPath));



			review.setOriginalFileName(originalFileName);
			review.setSaveFileName(saveFileName);
			review.setFilePath(fullPath);
			review.setFileDir(fileDir);

			
		}
		



		//review.setProduct(product);//리뷰한 상품번호 넣기

		//reviews.setRUser(rUser);
		//reviews.setPname(pname);
		review.setStar(star);
		review.setDate(LocalDateTime.now());//수정일
		review.setTitle(title);
		review.setContent(content);

		//db에 저장하는부분
		reviewRepository.save(review);//insert




	}

	//리뷰삭제
	public void deleteReview(Review review) {
		reviewRepository.delete(review);
	}

	//리뷰추천수 저장

	//인기순으로 리뷰셀렉











}
