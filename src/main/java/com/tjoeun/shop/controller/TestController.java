package com.tjoeun.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.shop.dtotest.Developer;
import com.tjoeun.shop.dtotest.Student;

@Controller
@RequestMapping("/thymeleaf")
public class TestController {
	@GetMapping("/test1")
	public String test1(Model model) {
		model.addAttribute("name", "더조은");
		model.addAttribute("age", "25");
		model.addAttribute("address", "정발산");
		return "thymeleaf/test1";
	}
	
	@GetMapping("/test2")
	public String test2(Student student) {
		
		student.setName("아이티");
		student.setAge(23);
		student.setSubject("컴퓨터공학과");
		
		return "thymeleaf/test2";
	}
	
	@GetMapping("/test3")
	public String test3(Developer developer, Model model) {
		
		developer.setName("아이티");
		developer.setAge(23);
		developer.setSubject("컴퓨터공학과");
		developer.setAddress("정발산");
		
		Developer dev1 = Developer.builder()
				                      .name("이순신")
				                      .build();
		
		Developer dev2 = Developer.builder()
                      				.name("이순신")
                      				.age(56)
                      				.build();
		
		Developer dev3 = Developer.builder()
				                      .age(47)
				                      .address("마두")
                      				.build();
		
		Developer dev4 = Developer.builder()
				                      .name("강감찬")
                        			.age(72)
                        			.subject("인공지능학과")
                        			.address("마두")
                        			.build();
		
		model.addAttribute("dev1", dev1);
		model.addAttribute("dev2", dev2);
		model.addAttribute("dev3", dev3);
		model.addAttribute("dev4", dev4);
		
		return "thymeleaf/test3";
	}
	
	@GetMapping("/test4")
	public void test4() {
		
	}
	
}




