package com.tjoeun.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tjoeun.shop.dto.MemberDto;
import com.tjoeun.shop.entity.Member;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
// @Transactional
@Slf4j
class MemberServiceTest {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberService memberService;
	
	public Member createMember() {
		MemberDto memberDto = MemberDto.builder()
				                           .name("더조은")
				                           .email("spring@tjoeun.com")
				                           .password("1234")
				                           .address("일산시 장항동")
				                           .build();
		
    Member member = Member.createMember(memberDto, passwordEncoder);
    
    
    return member;
	}

	@Test
	@DisplayName("회원 가입 테스트")
	public void saveMemberTest() {
		// MemberDto -> Member Entity -> DB member table
		Member member = createMember();
		log.info(">>>>>>>>>>>>>>>> member : " + member);
		
	  // 가입하는 회원 정보 DB 에 저장하기
    Member savedMember = memberService.saveMember(member);
    log.info(">>>>>>>>>>>>>>>> savedMember : " + savedMember);
    
    assertEquals(member.getName(), savedMember.getName());
    assertEquals(member.getEmail(), savedMember.getEmail());
    assertEquals(member.getPassword(), savedMember.getPassword());
    assertEquals(member.getAddress(), savedMember.getAddress());
    assertEquals(member.getRole(), savedMember.getRole());
    
	}
	
	@Test
	@DisplayName("가입하려는 회원이 이미 있는 회원인지 중복 테스트하기")
	public void saveDuplicatedMemberTest() {
		Member member1 = createMember();
		Member member2 = createMember();
		
		// member1 를 DB 에 저장하기
		memberService.saveMember(member1);
		
	  // member2 를 DB 에 저장하기
		Throwable e = assertThrows(IllegalStateException.class, 
				                      () -> memberService.saveMember(member2)); 
		
		assertEquals("이미 있는 회원입니다.", e.getMessage());
    
	}
	
}






