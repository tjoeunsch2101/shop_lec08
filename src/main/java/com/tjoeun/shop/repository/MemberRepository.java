package com.tjoeun.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.shop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
  Optional<Member> findByEmail(String email);
  
  
}
