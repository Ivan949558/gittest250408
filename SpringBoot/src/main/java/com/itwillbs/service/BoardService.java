package com.itwillbs.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itwillbs.domain.BoardDTO;
import com.itwillbs.entity.Board;
import com.itwillbs.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class BoardService {
	
	private final BoardRepository boardRepository;

	public void save(BoardDTO boardDTO) {
		log.info("BoardService save()");
		
		Board board = Board.setBoardEntity(boardDTO);
		
		boardRepository.save(board);
	}

	public List<Board> getAllBoards() {
		log.info("BoardService getAllBoards()");
		
		return boardRepository.findAll();
	}
	
    public Page<Board> getPagedBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Page<Board> getPagedBoardsBySubject(String subject, Pageable pageable) {
        return boardRepository.findBySubjectContaining(subject, pageable);
    }

	
	
}
