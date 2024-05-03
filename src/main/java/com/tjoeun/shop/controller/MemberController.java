package com.tjoeun.shop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.shop.dto.MemberDto;
import com.tjoeun.shop.entity.Member;
import com.tjoeun.shop.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	
	private final MemberService memberService;

	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/signup")
	public String signup(MemberDto memberDto) {
		
		return "member/signup";
	}
	
	@PostMapping("/signup_proc")
  public String signup_proc(@Valid MemberDto memberDto, BindingResult result,
  		                      Model model) {
		
		log.info(">>>>>>>>>>>>>>>> memberDto : " + memberDto);
		
		if(result.hasErrors()) {
			return "member/signup";
		}
		
		try {
		  Member member = Member.createMember(memberDto, passwordEncoder);
  		log.info(">>>>>>>>>>>>>>>> member : " + member);
  		Member savedMember = memberService.saveMember(member);
  		log.info(">>>>>>>>>>>>>>>> savedMember : " + savedMember);
		}catch(IllegalStateException e) {
			model.addAttribute("errorMsg", e.getMessage());
			return "member/signup";
		}
		
		return "member/signin";
	}
	
	@GetMapping("/signin")
	public String signin() {
		log.info(">>>>>>>>>>>>>>>> sigin");
		return "member/signin";
	}
	
	@GetMapping("/signin/error")
	public String loginError(Model model) {
		model.addAttribute("signinErroMsg", "아이디나 비밀번호를 다시 확인해 주세요");
		return "member/signin";
	}
	
	
	@GetMapping("/signout")
	public String signout(HttpServletRequest request, HttpServletResponse response) {
		log.info(">>>>>>>>>>>>>>>> signout");
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// session 정보 삭제하기
		if(authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		
		return "redirect:/";
	}
	
		
	@GetMapping("/signin/accessDenied")
	public String accessDenied(Model model) {
		
		model.addAttribute("signinErroMsg", "로그아웃한 후 관리자로 로그인해 주세요");
		
		return "member/signin";
	}
	

}
