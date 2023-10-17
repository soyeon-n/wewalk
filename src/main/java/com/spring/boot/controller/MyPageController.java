package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.dto.Goods;
import com.spring.boot.dto.GoodsForm;
import com.spring.boot.dto.Pay;
import com.spring.boot.model.SiteUser;
import com.spring.boot.dto.Shipping;
import com.spring.boot.dto.ShippingForm;
import com.spring.boot.service.GoodsService;
import com.spring.boot.service.PayService;
import com.spring.boot.service.ShippingService;
import com.spring.boot.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/user")
@Controller
public class MyPageController {

    private static final List<MultipartFile> imagePaths = null;
    
    private final PayService payService;
    private final UserService userService;
    private final ShippingService shippingService;
    private final GoodsService goodsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MyPageController(PayService payService, UserService userService, ShippingService shippingService, GoodsService goodsService) {
        this.payService = payService;
        this.userService = userService;
        this.shippingService = shippingService;
        this.goodsService = goodsService;
    }

    @GetMapping("/mypage")
    public String myPage(Model model, Authentication authentication) {
    	
        if (authentication != null) {
        	
        	SiteUser user = userService.getUserByEmail(authentication.getName());
        	model.addAttribute("user", user);
        	
        	List<Goods> goods = goodsService.getAllProducts();
            model.addAttribute("goods", goods);
        	
        	return "myPage";

        }
        
        return "login";
    }
    

	@GetMapping("/mypage/change")
    public String change(Model model, Authentication authentication) {
       
		SiteUser user = userService.getUserByEmail(authentication.getName());
    	model.addAttribute("user", user);
		
    	//회원정보 수정 하기 전 비밀 번호 확인
        return "change";
        
    }
	
	@PostMapping("/mypage/checkPassword")
	public String checkPassword(@RequestParam("userPassword") String enteredPassword, Authentication authentication, Model model) {
	    
		// 사용자가 입력한 비밀번호와 서버에서 가져온 암호화된 비밀번호를 비교
	    SiteUser user = userService.getUserByEmail(authentication.getName());
	    
	    if (passwordEncoder.matches(enteredPassword, user.getPassword())) {
	        // 비밀번호가 일치하면 원하는 페이지로 리디렉션
	        return "redirect:/user/mypage/change/changeInfo";
	        
	    } else {
	    	
	        // 비밀번호가 일치하지 않을 때, 사용자에게 알림을 표시
	    	model.addAttribute("errorMessage", "비밀번호가 올바르지 않습니다. 다시 시도하세요.");
	        return "redirect:/user/mypage/change";
	    }
	}
	
	
    @GetMapping("/mypage/change/changeInfo")
    public String changeInfo(Model model, Authentication authentication) {
       
    	//회원정보 수정탭
    	SiteUser user = userService.getUserByEmail(authentication.getName());
    	
    	model.addAttribute("birthYear", user.getBirthDate().getYear());
    	model.addAttribute("birthMonth", user.getBirthDate().getMonth().getValue());
    	model.addAttribute("birthDay", user.getBirthDate().getDayOfMonth());
    	
    	model.addAttribute("user", user);
    	
        return "changeInfo";
        
    }
    
    @PostMapping("/mypage/change/changeInfo")
    public String changeInfo(Model model, Authentication authentication, @RequestParam String originalPassword, @RequestParam String newPassword) {
        
    	// 현재 사용자 정보 가져오기
        SiteUser user = userService.getUserByEmail(authentication.getName());

        if (isPasswordValid(newPassword)) {
        	
            // 현재 비밀번호와 입력한 비밀번호를 비교
            if (passwordEncoder.matches(originalPassword, user.getPassword())) {
                // 새 비밀번호를 해싱하여 저장
                String encodedNewPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodedNewPassword);

                // SiteUser 업데이트
                userService.updatePassword(user, newPassword);

                return "redirect:/user/mypage";
                
            } else {
                
                return "redirect:/user/mypage/change/changeInfo"; 
            }
        } else {

            return "redirect:/user/mypage/change/changeInfo";
        }
    }
    	

    // 비밀번호 변경 조건을 확인하는 메서드
    private boolean isPasswordValid(String newPassword) {
        // 비밀번호의 조건을 여기에 추가합니다. 예를 들어, 길이, 숫자, 특수 문자 등
        if (newPassword.length() >= 6 && newPassword.matches(".*\\d.*") && newPassword.matches(".*[~!@#$%^&*()_+|<>?:{}].*")) {
            return true;
        }
        return false;
    }	
  
    

    
    @GetMapping("/mypage/shipping")
    public String shipping(Model model, HttpServletRequest request, Authentication authentication) {
        
    	// 배송지 관리 페이지
    	// 사용자의 배송지 목록을 가져오는 서비스 또는 메서드 호출
    	
    	SiteUser user = userService.getUserByEmail(authentication.getName());
        long userId = user.getId();
    	
        List<Shipping> shippingList = shippingService.getShippingListByUserId(userId);

        model.addAttribute("user", user);
        model.addAttribute("shippingList", shippingList);
    	
        return "shipping";
    }
    
    @PostMapping("/mypage/shipping")
    public String shipping(@ModelAttribute("ShippingForm") @Valid ShippingForm shippingForm, Model model, Authentication authentication) {
    	
    	

    	return "shipping";
    	
    }
    
    
    @PostMapping("/mypage/shipping/delete/{ano}")
    public String deleteShipping(@PathVariable("ano") Integer ano, Model model, Authentication authentication) {
    	
        // 주소 삭제 로직을 구현
        boolean deleted = shippingService.deleteAddress(ano);
        System.out.println(deleted);

        if (deleted) {
            // 삭제가 성공하면 배송지 목록을 다시 가져옵니다.
        	SiteUser user = userService.getUserByEmail(authentication.getName());
            long userId = user.getId();
        	
            List<Shipping> shippingList = shippingService.getShippingListByUserId(userId);
            
            model.addAttribute("user", user);
            model.addAttribute("shippingList", shippingList);
    
            
        } else {
            // 삭제에 실패한 경우 오류 메시지를 처리합니다.
            model.addAttribute("error", "주소 삭제에 실패했습니다.");
        }
        
        return "redirect:/user/mypage/shipping";
    }
 

    @GetMapping("/mypage/shipping_popup")
    public String shippingPopup(Model model, HttpServletRequest request) {
    	
    	return "shipping_popup";
    	
    }
    
    @PostMapping("/mypage/shipping_popup")
    public String shippingPopup(@ModelAttribute("ShippingForm") @Valid ShippingForm shippingForm, Model model, Authentication authentication) {
    	
    	//배송지 정보 관리
        // ShippingForm에서 Shipping 엔티티로 데이터 복사
    	Shipping shipping = new Shipping();
    	shipping.setReceiverName(shippingForm.getReceivername());
    	shipping.setType(shippingForm.getType());
        shipping.setPhone(shippingForm.getPhone());
        shipping.setZipcode(shippingForm.getZipcode());
        shipping.setAddress01(shippingForm.getAddress01());
        shipping.setAddress02(shippingForm.getAddress02());

        // 현재 로그인한 사용자 정보 가져오기
        SiteUser user = userService.getUserByEmail(authentication.getName());

        // Shipping 엔티티에 현재 로그인한 사용자 설정
        shipping.setUser(user);
        
        // Shipping 정보를 서비스를 통해 저장
        shippingService.saveShipping(shipping);
        
        //배송지 등록 성공 페이지
        return "shipping_success";
    	
    }
    	
    
    @GetMapping("/mypage/myshop")
    public String myShop(Model model, Authentication authentication) {
        
    	SiteUser user = userService.getUserByEmail(authentication.getName());
    	model.addAttribute("user", user);
        
        // user에 있는 사용자 ID를 기반으로 상품리스트(product 테이블) 가져오기
        //List<Goods> goodsList = GoodsRepository.findByAddedByUserId(id);
    	//model.addAttribute("goodsList", goodsList);
    	
        
        return "myshop";
    }
    
    @GetMapping("/mypage/myshop/add")
    public String myShopAdd(Model model, Authentication authentication) {
        
        //물건 등록 페이지
    	//현재 사용자의 user_id 가져오기
    	SiteUser user = userService.getUserByEmail(authentication.getName());
        long userId = user.getId();
        
        model.addAttribute("goodsForm", new GoodsForm());
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        
        return "myshop_add";
    }
    

    @PostMapping("/mypage/myshop/add")
    public String myShopCreate(@ModelAttribute("GoodsForm") @Valid GoodsForm goodsForm, Model model, Authentication authentication, BindingResult bindingResult) {
       
    	
    	// 상품을 등록하기 위한 POST 요청 처리
        if (bindingResult.hasErrors()) {
            // 입력 데이터의 유효성 검사 실패 시, 다시 폼을 보여줌
            return "myshop_add";
        }

        // 컨트롤러에서 선택된 태그를 GoodsForm에 설정
        goodsForm.setTag(goodsForm.getTag());
        
        // 파일 업로드 및 경로 저장
        List<String> imagePaths = new ArrayList<>();
        
        for (MultipartFile image : goodsForm.getImages()) {
            if (image != null && !image.isEmpty()) {
                
                String imagePath = saveImage(image);
                if (imagePath != null) {
                    imagePaths.add(imagePath);
                }
            }
        }
                
        // Goods 엔티티에 이미지 경로 저장
        Goods goods = new Goods();
        goods.setImage(imagePaths.size() >= 1 ? imagePaths.get(0) : null);
        goods.setImage1(imagePaths.size() >= 2 ? imagePaths.get(1) : null);
        goods.setImage2(imagePaths.size() >= 3 ? imagePaths.get(2) : null);
        goods.setImage3(imagePaths.size() >= 4 ? imagePaths.get(3) : null);
        goods.setImage4(imagePaths.size() >= 5 ? imagePaths.get(4) : null);

        // 상품 등록 로직
        //현재 사용자의 user_id 가져오기
        SiteUser user = userService.getUserByEmail(authentication.getName());
        long userId = user.getId();
        goodsService.registerProduct(goodsForm, goods, userId);

        return "redirect:/user/mypage";
                    

    }     

    
    private String saveImage(MultipartFile image) {
        // 이미지를 저장할 디렉토리 경로 설정 (application.properties에 설정한 경로 사용)
    	
        String uploadDirectory = "/src/main/resources/static/product/";
        

        // 업로드한 이미지 파일의 원래 파일 이름
        String originalFilename = image.getOriginalFilename();

        // 이미지 파일 이름에 UUID 추가하여 고유한 이름 생성
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFilename;

        Path imagePath = null; // 이미지 파일의 경로를 저장할 변수

        try {
            // 이미지를 저장할 디렉토리가 없으면 생성
            Files.createDirectories(Paths.get(uploadDirectory));

            // 이미지 파일을 저장할 경로 설정
            //imagePath = Paths.get(uploadDirectory, uniqueFileName);
            imagePath = Paths.get(uniqueFileName);

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
    
    
    @GetMapping("/mypage/mybuyhistory")
    public String myBuyHistory(Model model, Authentication authentication) {
        
        //구매 내역 페이지

    	SiteUser user = userService.getUserByEmail(authentication.getName());
        long userId = user.getId();
        
        model.addAttribute("goodsForm", new GoodsForm());
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        
        return "myBuyHistory";
    }
    

    @PostMapping("/mypage/mybuyhistory")
    public String myBuyHistory(@ModelAttribute("GoodsForm") @Valid GoodsForm goodsForm, Model model, Authentication authentication, BindingResult bindingResult) {
    	
    	return "myBuyHistory";
    	
    }
       

    @GetMapping("/mypage/pay")
    public String Pay (Model model, HttpServletRequest request, Authentication authentication) {
    	
    	//pay 충전 페이지
    	//사용자 정보 user에 담아서 model에 추가
    	SiteUser user = userService.getUserByEmail(authentication.getName());
    	model.addAttribute("user", user);
    	
        return "pay"; // payment.html 또는 결제 페이지 템플릿을 반환
    }
    
    @PostMapping("/mypage/pay")
    public String pay(Model model, HttpServletRequest request, Authentication authentication, @ModelAttribute("user") SiteUser user, @RequestParam("paymoney") Integer paymoney) {

    	
    	System.out.println(paymoney);
    	
    	//pay 충전 페이지
    	try { 
    		
    		Pay pay = new Pay();
    		pay.setUser(user);
    		pay.setPayMoney(paymoney);
    		pay.setType("충전");
    		pay.setPayDate(new Date());

            // 결제 정보를 데이터베이스에 저장
    		payService.save(pay);

            
        } catch (Exception e) {
            // 결제 실패 시 화면에 메시지 전달

            e.printStackTrace();
        }
        return "pay";
    }
    
    @GetMapping("/mypage/membership")
    public String membership(Model model, HttpServletRequest request) {
    	
    	//membership 가입 페이지
    	
    	return "membership";
    }
    
    @PostMapping("/mypage/membership")
    public String subscribeMembership(@RequestParam("email") String userEmail, Model model) {
    	
    	/*

        try {
        	
        	
            // 결제 API 호출 및 결제 정보 가져오기
            PaymentResponse paymentResponse = MembershipService.processPayment(userEmail);

            // 결제가 성공하면 멤버쉽 상태를 업데이트
            if (paymentResponse.isSuccess()) {
                MembershipService.updateMembershipStatus(userEmail);
                model.addAttribute("message", "멤버쉽 가입이 완료되었습니다.");
            } else {
                model.addAttribute("message", "결제가 실패하였습니다.");
            }
        } catch (Exception e) {
            // 결제 처리 중 예외 발생 시 처리
            model.addAttribute("message", "결제 처리 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }
		
*/
        return "/mypage";
        
    }
    
    @GetMapping("/mypage/grade")
    public String grade(Model model, HttpServletRequest request) {
    	
    	//구매등급 안내 페이지
    	
    	return "grade";
    }
    
    @GetMapping("/mypage/shippingList")
    public String shippingList(Model model, HttpServletRequest request) {
    	
    	//구매등급 안내 페이지
    
    	return "shipping_list";
    }
    
    @GetMapping("/mypage/withdraw")
    public String withdraw(Model model, Authentication authentication) {
    	
    	//회원 탈퇴 페이지
    	//사용자 정보 가져오기 (비밀번호 일치 할 경우 탈퇴 처리를 위해)
    	SiteUser user = userService.getUserByEmail(authentication.getName());
    	model.addAttribute("user", user);
    
    	return "withdraw";
    }
    
    @PostMapping("/mypage/withdraw")
    public String withDraw(@RequestParam("userPassword") String enteredPassword, Authentication authentication, Model model) {
        SiteUser user = userService.getUserByEmail(authentication.getName());

        if (passwordEncoder.matches(enteredPassword, user.getPassword())) {
            // 비밀번호가 일치할 경우 사용자 삭제
            userService.deleteUserById(user.getId());
            
            // 다른 테이블의 데이터도 삭제
            //userService.deleteRelatedDataByUserId(user.getId());
            return "withdraw_success";
            
        } else {
            model.addAttribute("errorMessage", "비밀번호가 올바르지 않습니다. 다시 시도하세요.");
            return "redirect:/user/mypage/withdraw";
        }
    }
    
    
    /*
    @GetMapping("/mypage/Paytest")
    public String Pay_test(Model model, Pay pay, Principal principal) {
    	
    	//Pay test
    	//사용자 정보 가져오기
    	String name = principal.getName();
        Member.rpMemberAll member = payService.findAll(name);
        //로그인이 되어있는 정보에서 모든 정보를 끌어옴
        //이후 모델에 넣고 값을 던져줌
        model.addAttribute("member", member);
        //스토어 객체를 스토어에 있는 모든 정보를 끌고와야댐
        Store realStore = store;
        model.addAttribute("store", realStore);
    
    	return "Pay_test";
    }
    
    @PostMapping("/mypage/Paytest")
    public String Pay_test(PayRequest payRequest, SiteUser user, Pay pay) {
     
    	//payRequestVO가 값이 비어있다면 no
        //값이 비어있지않다면 yes를 반환한다.
        String res = "no";
        System.out.println(payRequest.getImp_uid().toString());
        Member.rpMemberAll resMember = payService.findAll(member.getEmailId());
        //나중에 db에 여러가지 정보들이 들어갔을때 불러오기
        Store resStore = store;
        if(payRequest.getImp_uid() != null && payRequest.getMerchant_uid() != null){
            //값이 잘 들어와있다면 resMember, resStore를
            //payMember, payStore에 저장
            payMember = resMember;
            payStore = resStore;
            res = "yes";
        }
        return res;
    	
    }
    
    //결제 완료 확인후 값을 db에 저장
    @GetMapping(value = "/payclear")
    public String payClear(){
        //db에 결제정보를 저장한다
        System.out.println("결제 정보 저장 완료");
        return "/user/mypage/paytest";
    }
    
	*/
    
}

   
