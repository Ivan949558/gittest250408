package com.itwillbs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.itwillbs.domain.MemberDTO;
import com.itwillbs.entity.Member;
import com.itwillbs.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@Log
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	public void save(MemberDTO memberDTO) {
		log.info("MemberService save()");
		
		Member member = Member.setMemberEntity(memberDTO);
		
		memberRepository.save(member);
	}

	public Member findByIdAndPasswd(MemberDTO memberDTO) {
		log.info("MemberService findByIdAndPasswd()");
		
		return memberRepository.findByIdAndPasswd(memberDTO.getId(), memberDTO.getPasswd());
	}

	public Optional<Member> findById(String id) {
		log.info("MemberService FindById()");
		
		return memberRepository.findById(id);
	}

	public void updatePro(MemberDTO memberDTO) {
		log.info("MemberService updatePro()");
		
		Member member = Member.setMemberEntity(memberDTO);
		
		memberRepository.save(member);
	}

	public void delete(String id) {
		log.info("MemberService delete()");
		
		memberRepository.deleteById(id);
	}

	public List<Member> getAllMembers() {
		log.info("MemberService getAllMembers()");
		
		return memberRepository.findAll();
	}
	
	
	
	
}
