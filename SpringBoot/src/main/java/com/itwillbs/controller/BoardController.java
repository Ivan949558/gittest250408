package com.itwillbs.controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.itwillbs.domain.BoardDTO;
import com.itwillbs.domain.MemberDTO;
import com.itwillbs.entity.Board;
import com.itwillbs.entity.Member;
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
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	@GetMapping("/write")
	public String write() {
		log.info("BoardController write()");
		
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
	public String content(Model model, BoardDTO boardDTO, @RequestParam(name = "num") Integer num) {
		log.info("BoardController content()");
		Optional<Board> board = boardService.findByBoard(num);
		model.addAttribute("board", board.get());
		
		return "/board/content";
	}
	
	@GetMapping("/update")
	public String update(Model model, BoardDTO boardDTO, @RequestParam(name = "num") Integer num) {
		log.info("BoardController update()");
		Optional<Board> board = boardService.findByBoard(num);
		model.addAttribute("board", board.get());
		
		return "/board/update";
	}
	
	@PostMapping("/updatePro")
	public String updatePro(BoardDTO boardDTO, @RequestParam(name = "num") Integer num) {
		log.info("board updatePro()");
		log.info(boardDTO.toString());
		boardDTO.setNum(num);
        boardDTO.setRdate(new Timestamp(System.currentTimeMillis()));
		
		boardService.save(boardDTO);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(name = "num") Integer num) {
		log.info("BoardController deletePro()");
		
		boardService.delete(num);
		return "redirect:/board/list";
		
	}
	
	@GetMapping("/list")
	public String list(Model model, 
	                   @RequestParam(name = "page", defaultValue = "0", required = false) int page, 
	                   @RequestParam(name = "size", defaultValue = "10") int size, 
	                   @RequestParam(name = "paged", defaultValue = "true") boolean paged) {
	    log.info("게시판 리스트 조회");

	    if (paged) {
	        // 페이징된 데이터 조회
	        Pageable pageable = PageRequest.of(page, size);
	        Page<Board> boards = boardService.getPagedBoards(pageable);

	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", boards.getTotalPages());
	        model.addAttribute("boards", boards.getContent()); // 페이징된 데이터 추가
	        model.addAttribute("isPaged", true); // 템플릿에서 페이징 여부 확인용
	    } else {
	        // 전체 리스트 조회
	        List<Board> boards = boardService.getAllBoards();
	        model.addAttribute("boards", boards);
	        model.addAttribute("isPaged", false); // 템플릿에서 페이징 여부 확인용
	    }

	    return "/board/list";
	}
	
	@GetMapping("/fwrite")
	public String fwrite() {
		log.info("BoardController fwrite()");
		
		return "/board/fwrite";
	}
	
	@PostMapping("/fwritePro")
	public String fwritePro(@RequestParam("name") String name,
	                        @RequestParam("subject") String subject,
	                        @RequestParam("content") String content,
	                        @RequestParam("file") MultipartFile file) {
		log.info("BoardController fwritePro()");
	    String fileName = null;

	    if (!file.isEmpty()) {
	        fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); // UUID 사용하여 파일명 중복 방지
	        String uploadPath = uploadDir + fileName;

	        try {
	            file.transferTo(new File(uploadPath)); // 파일 저장
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    Board board = new Board();
	    board.setName(name);
	    board.setSubject(subject);
	    board.setContent(content);
	    board.setFile(fileName);
	    board.setRdate(new Timestamp(System.currentTimeMillis()));

	    boardService.fsave(board); // 엔터티 저장

	    return "redirect:/board/list";
	}
	
	@GetMapping("/fupdate")
	public String fupdate(Model model, BoardDTO boardDTO, @RequestParam(name = "num") Integer num) {
		log.info("BoardController fupdate()");
		
		Optional<Board> board = boardService.findByBoard(num);
		model.addAttribute("board", board.get());
		
		return "/board/fupdate";
	}
	
	@PostMapping("/fupdatePro")
	public String fupdatePro(@RequestParam("num") Integer num,
	                         @RequestParam("name") String name,
	                         @RequestParam("subject") String subject,
	                         @RequestParam("content") String content,
	                         @RequestParam("file") MultipartFile file) {
	    log.info("BoardController fupdatePro()");

	    // 기존 게시글 가져오기
	    Optional<Board> optionalBoard = boardService.findByBoard(num);
	    if (optionalBoard.isEmpty()) {
	        log.info("게시글을 찾을 수 없습니다: " + num);
	        return "redirect:/board/list";
	    }
	    
	    Board board = optionalBoard.get();

	    // 기존 정보 유지
	    board.setName(name);
	    board.setSubject(subject);
	    board.setContent(content);

	    // 파일 업로드 처리
	    if (!file.isEmpty()) {
	        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); // 중복 방지
	        String uploadPath = uploadDir + fileName;

	        try {
	            file.transferTo(new File(uploadPath)); // 파일 저장
	            board.setFile(fileName); // 새로운 파일로 업데이트
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } 
	    // 파일이 없으면 기존 파일 유지

	    board.setRdate(new Timestamp(System.currentTimeMillis())); // 수정 날짜 갱신
	    boardService.fsave(board); // 엔터티 저장

	    return "redirect:/board/list";
	}
	
	@GetMapping("/listJson")
	@ResponseBody
	public List<Board> listJson(@RequestParam(value = "page", defaultValue = "1", required = false)int page){
		
		int size = 10;
		
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("num").descending());
		
		Page<Board> boardList = boardService.getPagedBoards(pageable);
		
		return boardList.getContent();
	}
	
}
