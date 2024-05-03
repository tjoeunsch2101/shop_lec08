package com.tjoeun.shop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPIController {
	
	@GetMapping("/welcome")
	public String welcome() {
		return "RestAPIController Welcome !! Spring Boot !!!";
	}

}
