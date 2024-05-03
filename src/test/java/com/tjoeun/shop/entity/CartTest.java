package com.tjoeun.shop.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.shop.dto.MemberDto;
import com.tjoeun.shop.repository.CartRepository;
import com.tjoeun.shop.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Transactional
class CartTest {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Member insertMember() {
		
		MemberDto memberDto = MemberDto.builder()
				                           .name("양규")
				                           .email("yang@gmail.com")
				                           .password("123456")
				                           .address("흥화진")
				                           .build();
		
		Member member = Member.createMember(memberDto, passwordEncoder);
		return member;
	}

	@Test
	@DisplayName("장바구니 테스트")
	public void findCartAndMemberTest() {
		/*
		  Member Entity 클래스의 member_id 와
		  Cart Entity 클래스의 Member 의 member_id 가 같은지 test 하기
		*/
		Member member = insertMember();
		
		log.info(">>>>>>>>>>>>>>>> member (Entity) : " + member);
		
		Member savedMember = memberRepository.save(member);
		log.info(">>>>>>>>>>>>>>>> member (Entity) : " + member);
		
		Member foundMember = memberRepository.findById(savedMember.getId())
                                         .orElseThrow(EntityNotFoundException::new);
	  log.info(">>>>>>>>>>>>>>>> foundMember : " + foundMember);
		
		Cart cart = new Cart();
		cart.setMember(member);
		
		log.info(">>>>>>>>>>>>>>>> cart (Entity) : " + cart);
		
		Cart savedCart = cartRepository.save(cart);
		log.info(">>>>>>>>>>>>>>>> cart (Entity) : " + cart);
		
		Cart foundCart = cartRepository.findById(savedCart.getId())
		                               .orElseThrow(EntityNotFoundException::new);
		log.info(">>>>>>>>>>>>>>>> foundCart : " + foundCart);
		
		assertEquals(foundCart.getMember().getId(), foundMember.getId());

	}

}
