package com.itwillbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.java.Log;


@Controller
@Log
public class TestController {
	
	//http://localhost:8080/test1
	@GetMapping("/test1")
	public String test1() {
		
		return "test1";
	}
	
	//http://localhost:8080/test2
	@GetMapping("/test2")
	public String test2() {
		//test2.jsp => 에러발생
		return "test2";
	}
	
	
}
