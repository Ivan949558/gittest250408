package com.itwillbs.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.itwillbs.domain.BoardDTO;
import com.itwillbs.domain.MemberDTO;
import com.itwillbs.entity.Board;
import com.itwillbs.service.BoardService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@RequiredArgsConstructor
@Controller
@Log
@RequestMapping("/board/*")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/write")
	public String write() {
		log.info("board write()");
		
		return "/board/write";
	}
	
	@PostMapping("/writePro")
	public String writePro(BoardDTO boardDTO) {
		log.info("board writePro()");
		log.info(boardDTO.toString());
		LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        //boardDTO.setRdate(new Timestamp(System.currentTimeMillis()));
		boardDTO.setRdate(timestamp);
		boardDTO.setReadcount(0);
		
		boardService.save(boardDTO);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/content")
	public String content(HttpSession session, Model model, BoardDTO boardDTO) {
		log.info("MemberController content()");
		
		
		
		return "/board/content";
	}
	
	@GetMapping("/update")
	public String update() {
		log.info("MemberController update()");
		
		return "/board/update";
	}
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "10") int size) {
		log.info("board list()");
		
		List<Board> boards1 = boardService.getAllBoards();
		
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boards = boardService.getPagedBoards(pageable);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boards.getTotalPages());

		model.addAttribute("boards", boards1);
		
		return "/board/list";
	}
	
	@GetMapping("/fwrite")
	public String fwrite() {
		log.info("board fwrite()");
		
		return "/board/fwrite";
	}
	
	@PostMapping("/fwritePro")
	public String fwritePro() {
		log.info("board fwritePro()");
		
		return "redirect:/board/list";
	}
}
