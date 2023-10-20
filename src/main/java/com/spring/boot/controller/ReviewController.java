package com.spring.boot.controller;


import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.spring.boot.dto.PrincipalDetails;
import com.spring.boot.dto.ReviewForm;
import com.spring.boot.model.Product;
import com.spring.boot.model.Review;
import com.spring.boot.model.SiteUser;
import com.spring.boot.service.ProductService;
import com.spring.boot.service.ReviewService;
import com.spring.boot.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/review")//prefix
@RequiredArgsConstructor
@Controller
public class ReviewController {

	//서비스 추가
	private final ReviewService reviewService;
	private final ProductService productService;
	private final UserService userService;


	
	//원래이거는 productController 안에 들어있어서 layout 분리로 들어가야 한다 
	//Product product
	@RequestMapping("/list/{productNo}")
	public String reviewList(Model model , @PathVariable("productNo") Integer productNo,
			@PageableDefault Pageable pageable) {

		
		Product productnum = productService.getProductDetailByNo(productNo);
		
		Page<Review> paging = reviewService.getPnoReview(pageable, productnum);
		model.addAttribute("paging",paging);
		
		//return "product_review_list_temp";//제품리뷰>>미리보기식으로 이거를 layout 분리
		//return "product_review_list";
		//return "product_review_list_layout";
		return "product_qna_list";

	}





	////로그인한 사람만 들어올수 있음 
	//접근막기 추가 principal ?  >> d이걸 securityconfig 에서 처리 
	@RequestMapping("/mylist")
	public String myreviewList(Model model,
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@PageableDefault Pageable pageable) {

		//ruser따로 받아서 넣어주기 
		SiteUser user = userService.getUserByUserName(principalDetails.getUsername());
		
		//내리뷰 여러개는 Page? 로받나? List? 여러개는 아무튼 페이징해야해서 ? 
		Page<Review> paging = reviewService.getReview(user,pageable);

		model.addAttribute("paging",paging);

		return "mypage_review";
		
		//지금 로그인한사람이 쓴 리스트 뽑아오기 
		//지금 optional 안써서 지금 null 처리 안됨>>나 중에 수정 
		

	}

	//마이페이지에서 >> 리뷰쓰기
	@GetMapping("/create/{productNo}")
	public String createReview(ReviewForm reviewForm,
			@PathVariable("productNo")Integer productNo,
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			Model model){
		
		//여기서 product 정보 set해주기
		Product product = productService.getProductDetailByNo(productNo);
		SiteUser siteUser = userService.getUserByUserName(principalDetails.getUsername());
		//비로그인시 튕기기
		if(siteUser==null) {
			
			return "redirect:/product/detail/{productNo}";
		}
		
		model.addAttribute(product);//이안에 savefilename
		
		return "mypage_reviewReg";//리뷰작성 form 
	}


	//작성자의 로그인된 id 와 클릭해서 들어온 상품번호가 있어야 한다
	//리뷰등록
	@PostMapping("/create/{productNo}")
	public String createReview(Model model,
			@Valid ReviewForm reviewForm,@PathVariable("productNo") Integer productNo,
			MultipartFile multipartFile,
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			BindingResult bindResult) throws IOException {
		
		//상품번호ProductNo와 작성자 id 를 받아온다 
		
		SiteUser siteUser = userService.getUserByUserName(principalDetails.getUsername());
		
		Product product = productService.getProductDetailByNo(productNo);
		
		
		

		if(bindResult.hasErrors()) {			
			return "mypage_reviewReg";//form에 err 값 있을시 페이지 되돌려보내기 
		}

		//pname 을 product에서 자동으로 set 되도록하는거추가
		reviewService.createReview(product,siteUser, reviewForm.getPname(), 
				reviewForm.getStar(), reviewForm.getTitle(),reviewForm.getContent(), 
				multipartFile);


		//reviewForm.get ~ 으로 리뷰폼에 작성된 내용을 받아서 insert 한다 
		//어떤 것은 form 을 거쳐서 insert 하고 어떤것은 그냥 받은정보로 insert ? 

		return "redirect:/review/mylist";
		

	}

	//리뷰수정할값set하기
	//권한 제한 하도록 하기 
	@GetMapping("/modify/{id}")//리뷰글의 id 를 가져가야함 
	public String reviewModify(ReviewForm reviewForm,@PathVariable("id") Integer id) {
		 

		Review review = reviewService.getOneReview(id);
		//리뷰글의 고유id 로 하나의 리뷰 읽어오는 서비스 


		//if 로 권한검사할지말지 고민 로그인된상태에서만 myreview 들어올수있게할거기때문에 
		/*if(!question.getAuthor().getUserName().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다");
		}

		//아직 파일첨부 수정되는지 반영안됨 >> 새로 첨부해야지만 반영된다 


		 */
		//form 의 값을 다시 set 해준다 수정창에 미리 떠있도록 
		reviewForm.setTitle(review.getTitle());
		reviewForm.setContent(review.getContent());
		reviewForm.setPname(review.getPname());
		//reviewForm.setRUser(review.getRUser());//작성하는 작성자는그대로 
		reviewForm.setStar(review.getStar());




		return "mypage_reviewReg";//다시 리뷰작성창 돌려주기 




	}
	//리뷰할 값 다시 db 넣어주기
	@PostMapping("/modify/{id}")
	public String reviewModify(@Valid ReviewForm reviewForm,
			MultipartFile multipartFile,
			BindingResult bindResult,
			@PathVariable("id") Integer id) throws IOException{

		if(bindResult.hasErrors()) {
			return "question_form";
		}
		Review review = reviewService.getOneReview(id);
		
		//기존값 받아와가지고 
		
		//작성자=로그인된상태 검사 한번 할것 
		
		//reviewService.createReview(productnum,userId, reviewForm.getPname(), 
		//reviewForm.getStar(), reviewForm.getTitle(),reviewForm.getContent(), 
		//multipartFile);
		
		//다시insert 
		//ruser는 바꿀필요없는데 ?? reviewForm.getRUser() 없는데 흠
		reviewService.modifyReview(review, 
				reviewForm.getStar(), reviewForm.getTitle(), reviewForm.getContent(), multipartFile);
		
		return "redirect:/review/mylist";
		
	}

	//리뷰삭제 동시에 사진도 db 와로컬에서 삭제되어야함 
	@GetMapping("/delete/{id}")//id=리뷰글번호
	public String reviewDelete(@PathVariable("id") Integer id, Principal principal) {
		
		Review review = reviewService.getOneReview(id);
		
		reviewService.deleteReview(review);
		
		return "redirect:/review/mylist";
		
	}
	//리뷰좋아요 기능 




}
