package com.spring.boot.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.config.PwUpdate;
import com.spring.boot.config.SessionConst;
import com.spring.boot.dao.GoodsRepository;
import com.spring.boot.dto.Goods;
import com.spring.boot.dto.GoodsForm;
import com.spring.boot.dto.MyPage;
import com.spring.boot.service.GoodsService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MyPageController {

    @GetMapping("/mypage")
    public String myPage(Model model, HttpServletRequest request) {
    	
        HttpSession session = request.getSession();
        MyPage loginMember = (MyPage) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);
        
        MyPage dummyUser = new MyPage();
    	dummyUser.setName("name");
    	model.addAttribute("loginMember", dummyUser);
        
        return "mypage";
    }

    @GetMapping("/mypage/changePW")
    public String changePWForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        MyPage loginMember = (MyPage) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("pwUpdate", new PwUpdate());
        return "changePW";
    }
    
    @GetMapping("/mypage/member")
    public String member(Model model, HttpServletRequest request) {
        
    	
    	//회원정보 수정탭
    	
        return "member";
    }
    
    @GetMapping("/mypage/shipping")
    public String shipping(Model model, HttpServletRequest request) {
        
    	
    	//배송지 등록, 수정, 삭제
    	
        return "shipping";
    }
    
    @GetMapping("/mypage/myshop")
    public String myShop(Model model) {
        
        //나의 상점 - 여기선 로그인 되있는 상태니까 주소에 {name} 없어도 될 것 같음
    	
    	// 현재 로그인한 사용자의 정보 가져오기
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String name = authentication.getName();
        
        // 사용자 ID를 기반으로 상품 리스트 가져오기
        //List<Goods> goodsList = GoodsRepository.findByAddedByUserId(name);
    	List<Goods> goodsList = GoodsRepository.findByAddedByUserEmail("user1@example.com");
    	model.addAttribute("goodsList", goodsList);
        
        // 모델에 데이터를 추가
        //model.addAttribute("goodsList", goodsList);
    	
        
        return "myshop";
    }
    
    @GetMapping("/mypage/myshop/add")
    public String myShopAdd(Model model, HttpServletRequest request) {
        
        //물건 등록 페이지
    	model.addAttribute("goodsForm", new GoodsForm());
        
        return "myshop_add";
    }
    
    @Autowired
    private GoodsService goodsService;

    @PostMapping("/mypage/myshop/add")
    public String myShopCreate(@Valid @ModelAttribute("goodsForm") GoodsForm goodsForm, BindingResult bindingResult) {
        // 상품을 등록하기 위한 POST 요청 처리
        if (bindingResult.hasErrors()) {
            // 입력 데이터의 유효성 검사 실패 시, 다시 폼을 보여줌
            return "myshop_add";
        }
        
        // 파일 업로드 및 경로 저장
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile image : goodsForm.getImages()) {
            if (!image.isEmpty()) {
                // 이미지를 파일 시스템에 저장하고 경로를 반환
                String imagePath = saveImage(image);
                
                if (imagePath != null) {
                    imagePaths.add(imagePath);
                }
            }
        }
                
         // Goods 엔티티에 이미지 경로 저장
            Goods goods = new Goods();
            goods.setGdimage1(imagePaths.size() >= 1 ? imagePaths.get(0) : null);
            goods.setGdimage2(imagePaths.size() >= 2 ? imagePaths.get(1) : null);
            goods.setGdimage3(imagePaths.size() >= 3 ? imagePaths.get(2) : null);
            goods.setGdimage4(imagePaths.size() >= 4 ? imagePaths.get(3) : null);
            goods.setGdimage5(imagePaths.size() >= 5 ? imagePaths.get(4) : null);
        
                
        //상품 등록 로직
        goodsService.registerProduct(goodsForm);
        
        return "redirect:/mypage/myshop";
    }   
    
    @GetMapping("/mypage/pay")
    public String pay(Model model, HttpServletRequest request) {
    	
    	//pay 충전 페이지
    	
    	return "pay";
    }
    
    @GetMapping("/mypage/membership")
    public String membership(Model model, HttpServletRequest request) {
    	
    	//membership 가입 페이지
    	
    	return "membership";
    }
    
    @GetMapping("/mypage/grade")
    public String grade(Model model, HttpServletRequest request) {
    	
    	//구매등급 안내 페이지
    	
    	return "grade";
    }
    
    
    //사진 저장 메서드
    private String saveImage(MultipartFile image) {
        String imagePath = "/path/to/save/images"; // 이미지를 저장할 경로 설정
        String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        try {
            byte[] bytes = image.getBytes();
            Path filePath = Paths.get(imagePath, imageName);
            Files.write(filePath, bytes);
            return "/images/" + imageName; // 실제 웹에서 접근 가능한 경로로 저장
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리 필요
            return null;
        }
    }
    
    
    /*

    @PostMapping("/mypage/me")
    public String userEdit(MemberForm form, BindingResult result, @AuthenticationPrincipal Member currentMember) {
        if(result.hasErrors()) {
            return "redirect:/mypage/me";
        }
        
        memberService.updateInfo(currentMember.getUsername(), form.getName(), form.getEmail());
        currentMember.setUsername(form.getName());
        currentMember.setEmail(form.getEmail());

        return "redirect:/mypage/me";
    }


    @PostMapping("/myPage/changePW")
    public String update(Model model, @ModelAttribute @Valid PwUpdate pwUpdate, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            // 에러 처리 로직 추가
            return "/changePW";
        }

        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);

        // pwUpdate 객체에 loginId를 설정하지 않아도 됩니다.
        // 비밀번호 변경 후 로그아웃 처리
        session.invalidate();

        // myPageService.updatePw(pwUpdate); // 비밀번호 업데이트 로직을 호출

        return "redirect:/myPage"; // 로그인 페이지로 리다이렉트 또는 다른 페이지로 이동
    }
    */
}

   
