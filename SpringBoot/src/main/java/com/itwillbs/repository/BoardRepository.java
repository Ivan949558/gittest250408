package com.itwillbs.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwillbs.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	// 전체 게시글 페이징 처리
    Page<Board> findAll(Pageable pageable);

    // 특정 제목을 포함하는 게시글 페이징 처리
    Page<Board> findBySubjectContaining(String subject, Pageable pageable);


	
}
