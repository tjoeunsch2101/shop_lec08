package com.tjoeun.shop.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tjoeun.shop.entity.Member;
import com.tjoeun.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	
	public Member saveMember(Member member) {
		validateDuplicatedMember(member);
		return memberRepository.save(member);
	}
	
	// 이미 가입한 회원이 있는지 검사하기
	public void validateDuplicatedMember(Member member){
		
		// argument 로 넣은 이메일(ember.getEmail()) 로 
    // 이미 가입한 회원이 있는지 검사하기
		Optional<Member> foundMember =  memberRepository.findByEmail(member.getEmail());
		
		// DB 에 저장된 회원 정보
	  if(foundMember.isPresent()) {
	  	System.out.println("이미 가입한 회원 이름 : " + foundMember.get().getName());
		  throw new IllegalStateException("이미 있는 회원입니다.");
	  }
		
	}
	
	/*
	  loadUserByUsername() 메소드를 구현함
         ㄴ 회원 정보를 조회해서 
            해당 회원의 정보와 권한을 갖는 UserDetail interface 구현체를 반환함
	*/
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		Member member = 
				memberRepository.findByEmail(email)
				                .orElseThrow(() -> new UsernameNotFoundException(email + " 을 사용하는 회원이 없습니다"));
		
		log.info(">>>>>>>>>>>>>>>> [로그인한 회원] : " + member);
		
		return User.builder()
				       .username(member.getName())
		           .password(member.getPassword())
		           .roles(member.getRole().toString())
		           .build();
		               
	}
	
	
	
	

}
