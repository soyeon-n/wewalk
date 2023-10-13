package com.spring.boot.controller;


import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


import com.spring.boot.dto.ReviewForm;
import com.spring.boot.model.Product;
import com.spring.boot.model.Review;

import com.spring.boot.service.ProductService;
import com.spring.boot.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/review")//prefix
@RequiredArgsConstructor
@Controller
public class ReviewController {
	
	//서비스 추가
	private final ReviewService reviewService;
	private final ProductService productService;
	
	
	//그 상품의 리뷰리스트= {productNo} 가 있어야 할것 같은데 ?? 
	@RequestMapping("/list")
	public String reviewList(Model model , @PageableDefault Pageable pageable) {
		
		Page<Review> paging = reviewService.getTotalReview(pageable);
		model.addAttribute("paging",paging);
				
		//return "product_review_list";//제품리뷰
		return "product_list";
		
	}
	
	
	
	
	
	////로그인한 사람만 들어올수 있음
	//내가쓴리뷰리스트 {rUser} // 근데 원래는 user 정보를 주소로주면 안되고 세션에 숨기거나 그럴것임 
	@RequestMapping("/mylist/{rUser}")
	public String myreviewList(Model model,
			@PathVariable("rUser") Integer rUser,@PageableDefault Pageable pageable) {
		
		//ruser따로 받아서 넣어주기 
		//SiteUser user = userService.getUserByEmail(authentication.getName());
		//내리뷰 여러개는 Page? 로받나? List? 여러개는 아무튼 페이징해야해서 ? 
		Page<Review> paging = reviewService.getReview(rUser,pageable);
		
		model.addAttribute("paging",paging);
		
		return "mypage_review";//내가쓴 리뷰들 리스트 
		//지금 로그인한사람이 쓴 리스트 뽑아오기 
		//지금 optional 안써서 지금 null 처리 안됨>>나 중에 수정 
		
	}
	
	
	//@PreAuthorize//권한제한
	//마이페이지에서 >> 리뷰쓰기
	@GetMapping("/create/{productNo}")
	public String createReview(ReviewForm reviewForm){
		
		return "mypage_reviewReg";//리뷰작성 form 
	}
	
	
	//작성자의 로그인된 id 와 클릭해서 들어온 상품번호가 있어야 한다
	//리뷰등록
	@PostMapping("/create/{productNo}")
	public String createReview(Model model,
			@Valid ReviewForm reviewForm,@PathVariable("productNo") Integer productNo,
			MultipartFile multipartFile,
			
			BindingResult bindResult) throws IOException {
		//Principal principal 안넣은 상태
		
		
		//상품번호ProductNo와 작성자 id 를 받아온다 
		Integer userId = 1;//임시아이디
		//SiteUser siteUser = userService.getUser(principal.getName());
		
		//+++상품명pname , productNo 가 알아서 리뷰입력시에 들어가도록하기 
		 
		Product productnum = productService.getProductDetailByNo(productNo);
		//Integer productnum = productNo;//{productNo}값 그대로 넣어주기
		//이렇게 안어가서 Product product 이렇게 넣는듯 
		 
		//pname 자동입력
		//Product productName = productService.getProductDetailByNo(productNo);
		//파일업로드
		
		
		if(bindResult.hasErrors()) {			
			return "mypage_reviewReg";//form에 err 값 있을시 페이지 되돌려보내기 
		}
 
		//pname 을 product에서 자동으로 set 되도록하는거추가
		reviewService.createReview(productnum,userId, reviewForm.getPname(), 
				reviewForm.getStar(), reviewForm.getTitle(),reviewForm.getContent(), 
				multipartFile);
		
		
		//reviewForm.getFileDTO()?multipartFile
		//reviewForm.get ~ 으로 리뷰폼에 작성된 내용을 받아서 insert 한다 
		//어떤 것은 form 을 거쳐서 insert 하고 어떤것은 그냥 받은정보로 insert ? 
		
		return "redirect:/review/mylist";
		 
		//주소돌려주기수정))지금접속되어있는 userId 를 가지고 돌아와야 다시 로그인된상태의 내 리뷰 보기가 될거아녀
		//세션에 올라가있으면 자동으로 인식하나 ?? 
		
	}
	//리뷰삭제,수정,
	
	
	

}
