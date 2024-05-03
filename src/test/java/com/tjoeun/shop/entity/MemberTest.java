package com.tjoeun.shop.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.shop.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
class MemberTest {
	
	@Autowired
	private MemberRepository memberRepository;
	
	// @PersistenceContext
	@Autowired
	private EntityManager entityManager;

	@Test
	@DisplayName("Auditing Test")
	@WithMockUser(username="tjoeun", roles="USER")
	public void AuditingTest() {
		
		// Member meber = new Member();
		Member member = Member.builder().build();
		log.info(">>>>>>>>>>>>>>>> member (Entity) : " + member);
		
		Member savedMember = memberRepository.save(member);
		log.info(">>>>>>>>>>>>>>>> savedMember : " + savedMember);
		log.info(">>>>>>>>>>>>>>>> member (Entity) : " + member);
		
		entityManager.flush();
		entityManager.clear();
		
		Member foundMember = memberRepository.findById(savedMember.getId())
				                                 .orElseThrow(EntityNotFoundException::new);
		
		log.info(">>>>>>>>>>>>>>>> foundMember : " + foundMember);
		
		log.info(">>>>>>>>>>>>>>>> item register time : " + member.getRegTime());
		log.info(">>>>>>>>>>>>>>>> item update   time : " + member.getUpdateTime());
		log.info(">>>>>>>>>>>>>>>> create member : " + member.getCreatedBy());
		log.info(">>>>>>>>>>>>>>>> modify member : " + member.getModifiedBy());
		
		
	}

}
