package com.tjoeun.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "thymeleaf/test4";
	}
	
	@GetMapping("/restAPItest")
	@ResponseBody
	public String restAPItest() {
		return "restAPItest";
	}
	
	
}
