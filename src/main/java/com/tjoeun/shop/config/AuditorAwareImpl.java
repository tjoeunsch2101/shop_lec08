package com.tjoeun.shop.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		// Spring Security 에서 로그인한 회원의 정보 가져오기 : 인증 정보 확인하기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String userId = "";
		if(authentication != null) {
			userId = authentication.getName();
			log.info(">>>>>>>>>>>>>>>> userId : " + userId);
		}
		
		// Optional.of(userId) : null 이 아닌 값으로 Optional 객체를 생성함
		return Optional.of(userId);
		
	}
	
	

}
