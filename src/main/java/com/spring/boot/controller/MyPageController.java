package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.spring.boot.config.PwUpdate;
import com.spring.boot.config.SessionConst;
import com.spring.boot.dto.MyPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public String myShop(Model model, @PathVariable String name) {
        model.addAttribute("name", name);
        
        //나의 상점 - 여기선 로그인 되있는 상태니까 주소에 {name} 없어도 될 것 같음
        
        return "myshop";
    }
    
    @GetMapping("/mypage/myshop/add")
    public String myShopAdd(Model model, HttpServletRequest request) {
        
        
        //물건 등록 페이지
        
        return "myshop_add";
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

   
