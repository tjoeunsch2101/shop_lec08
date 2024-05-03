package com.tjoeun.shop.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.tjoeun.shop.audit.BasicEntity;
import com.tjoeun.shop.constant.Role;
import com.tjoeun.shop.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BasicEntity{
	
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="member_id")
	private Long id;
	
	private String name;
	
	@Column(unique=true)
	private String email;
	
	private String password;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	public static Member createMember(MemberDto memberDto, 
			                              PasswordEncoder passwordEncoder) {
		
		String password = passwordEncoder.encode(memberDto.getPassword());
		Member member = Member.builder()
				                  .name(memberDto.getName())
				                  .email(memberDto.getEmail())
				                  .password(password)
				                  .address(memberDto.getAddress())
				                  .role(Role.USER)
				                  .build();
		
		return member;
		
	}
	

}
