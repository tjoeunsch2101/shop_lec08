package com.tjoeun.shop.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		/*
		http.formLogin(Customizer.withDefaults())
		    
		*/
		
		// 로그인 처리 부분
		// .usernameParameter("username") 이 default 값임
		// .passwordParameter("password") 이 default 값임
		// 이메일을 사용해서 로그인하는 경우에는
		// .usernameParameter("email") <-- 을 작성해야 함
		http.formLogin(form -> form
				                   .loginPage("/member/signin")
				                   .defaultSuccessUrl("/", true)
				                   .failureUrl("/member/signin/error")
				                   .usernameParameter("email")
				                   .permitAll());
		
		// 로그아웃 처리 부분
		http.logout(Customizer.withDefaults());
		
		// 페이지에 대한 접근권한 설정 부분
		http.authorizeHttpRequests(request -> request
				                                  .requestMatchers("/css/**").permitAll()
				                                  .requestMatchers("/", "/member/**").permitAll()
				                                  .requestMatchers("/admin/**").hasRole("ADMIN")
				                                  .anyRequest().authenticated());
		
		/*
		http.exceptionHandling(exception -> exception
				                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));
		*/
		
		// 로그아웃한 후 관리자로 로그인 요청하기
		http.exceptionHandling(exception -> exception
		                                   .accessDeniedHandler(new CustomAccessDeniedHandler()));
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}





