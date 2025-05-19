package com.itwillbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.itwillbs.domain.MemberDTO;

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
	
	//http://localhost:8080/test3
	//MemberDTO 객체 생성
	//set 메서드 호출 "kim", "p123", "김길동"
	//model 담아서("memberDTO", memberDTO)
	//test3.html 이동
	@GetMapping("/test3")
	public String test3(Model model) {
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId("kim");
		memberDTO.setName("김길동");
		memberDTO.setPasswd("p123");
		
		model.addAttribute("memberDTO", memberDTO);
		
		return "test3";
	}
	
	
	
	
}
