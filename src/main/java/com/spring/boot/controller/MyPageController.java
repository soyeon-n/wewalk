package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.spring.boot.config.PwUpdate;
import com.spring.boot.config.SessionConst;
import com.spring.boot.dto.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MyPageController {

    @GetMapping("/mypage")
    public String myPage(Model model, HttpServletRequest request) {
    	
    	Member dummyUser = new Member();
    	dummyUser.setName("John Doe");
    	model.addAttribute("loginMember", dummyUser);
    	
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);
        return "mypage";
    }

    @GetMapping("/mypage/changePW")
    public String changePWForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("pwUpdate", new PwUpdate());
        return "changePW";
    }
    
    /*
    @GetMapping("/mypage/me")
    public String myPageHome(Model model, @AuthenticationPrincipal Member currentMember) {
        List<Category> categoryList = categoryService.findAll();

        MemberForm memberForm = new MemberForm();
        memberForm.setName(currentMember.getUsername());
        memberForm.setEmail(currentMember.getEmail());
        
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("memberForm", memberForm);
        return "mypage/me";
    }
    
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

   
