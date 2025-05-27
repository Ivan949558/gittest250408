package com.itwillbs.controller;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.SessionScope;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.entity.Member;
import com.itwillbs.service.MemberService;
import com.mysql.cj.Session;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Controller
@Log
@RequiredArgsConstructor
public class MemberController {
	
	//객체 생성
	private final MemberService memberService;
	
	@GetMapping("/")
    public String home() {
        return "/member/main";
    }

	
	//http://localhost:8080/insert
	@GetMapping("/insert")
	public String insert() {
		log.info("MemberController insert()");
		
		return "/member/insert";
	}
	
	//http://localhost:8080/insertPro
	@PostMapping("/insertPro")
	public String insertPro(MemberDTO memberDTO) {
		log.info("MemberController insertPro()");
		log.info(memberDTO.toString());
		
		memberService.save(memberDTO);
		
		return "redirect:/login";
	}
	
	//http://localhost:8080/login
	@GetMapping("/login")
	public String login() {
		log.info("MemberController login()");
		
		return "/member/login";
	}
	
	@GetMapping("/loginWarning")
	public String loginWarning() {
		log.info("MemberController login()");
		
		return "/member/loginWarning";
	}
	
	//http://localhost:8080/login
	@PostMapping("/loginPro")
	public String loginPro(MemberDTO memberDTO, HttpSession session) {
		log.info("MemberController loginPro()");
		
		Member member = memberService.findByIdAndPasswd(memberDTO);
		
		if(member != null) {
			session.setAttribute("id", member.getId());
			return "redirect:/main";
		} else {
			return "redirect:/loginWarning";
		}
	}
	
	//http://localhost:8080/main
	@GetMapping("/main")
	public String main() {
		log.info("MemberController main()");
		
		return "/member/main";
	}
	
	//http://localhost:8080/logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		log.info("MemberController logout()");
		
		session.invalidate();
		
		return "redirect:/main";
	}
	
	//http://localhost:8080/info
	@GetMapping("/info")
	public String info(HttpSession session, Model model, MemberDTO memberDTO) {
		log.info("MemberController info()");
		String id = (String) session.getAttribute("id");
		
		Optional<Member> member = memberService.findById(id);
		model.addAttribute("member", member.get());
		
		return "/member/info";
	}
	
	//http://localhost:8080/update
	@GetMapping("/update")
	public String update(HttpSession session, Model model) {
		log.info("MemberController main()");
		String id = (String) session.getAttribute("id");
		Optional<Member> member = memberService.findById(id);
		model.addAttribute("member", member.get());
		
		return "/member/update";
	}
	
	//http://localhost:8080/updatePro
	@PostMapping("/updatePro")
	public String updatePro(MemberDTO memberDTO, HttpSession session, @RequestParam("passwd") String passwd) {
		log.info("MemberController logout()");
		
		Member member = memberService.findByIdAndPasswd(memberDTO);
		
		if(member != null) {
			//수정하러 가기전에 수정되지 않는 값도 담아서 들고감
			// => 안들고 가면 기존값 null 수정이 되어짐
			//memberDTO.setRdate(member.getRdate());
			
			memberService.updatePro(memberDTO);
			return "redirect:/main";
		} else {
			return "redirect:/update";
		}
	}
	
	@GetMapping("/delete")
	public String delete() {
		log.info("MemberController delete()");
		
		return "/member/delete";
	}
	
	//http://localhost:8080/deletePro
	@PostMapping("/deletePro")
	public String deletePro(HttpSession session, MemberDTO memberDTO) {
		log.info("MemberController deletePro()");
		Member member = memberService.findByIdAndPasswd(memberDTO);
		
		if(member != null) {
			memberService.delete(member.getId());
			return "redirect:/logout";
		} else {
			return "redirect:/main";
		}
	}
	
	//http://localhost:8080/list
	@GetMapping("/list")
	public String list(Model model) {
		log.info("MemberController list()");
		
		
		List<Member> members = memberService.getAllMembers();
		log.info("member" + members);
        model.addAttribute("members", members);
		
		return "member/list";
	}
	
	
}
