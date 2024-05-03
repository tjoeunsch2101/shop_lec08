package com.tjoeun.shop.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import com.tjoeun.shop.dto.MemberDto;
import com.tjoeun.shop.entity.Member;
import com.tjoeun.shop.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
  private	PasswordEncoder passwordEncoder;
	
	public Member insertMember(String email, String password) {
		
		MemberDto memberDto = MemberDto.builder()
				                           .email(email)
				                           .password(password)
				                           .name("정약용")
				                           .address("경기도 광주")
				                           .build();  
		
		// 회원 정보를  Entity 클래스인 Member 객체에 저장함
		Member member = Member.createMember(memberDto, passwordEncoder);
		
		// Entity 클래스인 Member 객체에 저장된 내용을 database 에 저장함
		//                                               ㄴ JpaRepository
		// memberService.saveMember(member) 를 호출하면
		// JpaRepository 를 상속한 MemberRepository 의 save() 메소드가 호출됨
		Member savedMember = memberService.saveMember(member);
		return savedMember;
	}
	

	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccessTest() throws Exception {
		
		String email = "spinglogin@tjoeun.com";
		String password = "123456";
		// Member savedMember = this.insertMember(email, password);
		// 이메일이 "spinglogin@tjoeun.com" 이고 비밀번호가 "123456" 인
		// 회원 정보를 member 테이블에 저장함
		this.insertMember(email, password);
		
	  // 이메일이 "spinglogin@tjoeun.com" 이고 비밀번호가 "123456" 인
	  // 회원 정보로 로그인을 수행함
		mockMvc.perform(formLogin()
				            .userParameter("email")
				            .loginProcessingUrl("/member/signin")
				            .user(email)
				            .password(password))
				            .andExpect(SecurityMockMvcResultMatchers.authenticated());
		
  }

	@Test
	@DisplayName("로그인 실패 테스트")
	public void loginFailureTest() throws Exception {
		
		String email = "spinglogin@tjoeun.com";
		String password = "123456";
		// Member savedMember = this.insertMember(email, password);
		// 이메일이 "spinglogin@tjoeun.com" 이고 비밀번호가 "123456" 인
		// 회원 정보를 member 테이블에 저장함
		this.insertMember(email, password);
		
	  // 이메일이 "spinglogin@tjoeun.com" 이고 비밀번호가 "123456" 인
	  // 회원 정보로 로그인을 수행함
		mockMvc.perform(formLogin()
				            .userParameter("email")
				            .loginProcessingUrl("/member/signin")
				            .user(email)
				            .password("987654"))
				            .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
		
  }
	
}
