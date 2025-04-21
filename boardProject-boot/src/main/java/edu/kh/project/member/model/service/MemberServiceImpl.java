package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;

@Transactional(rollbackFor = Exception.class)
@Service // 비즈니스로직 처리 역할 명시 + Bean 등록
@Slf4j
public class MemberServiceImpl implements MemberService{
	
	// 등록된 Bean 중에서 MemberMapper와 같은 타입 or 상속관계인 Bean을 찾아
	@Autowired // 의존성 주입(DI)
	private MemberMapper mapper;
	
	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		// 암호화 진행
		
		// bcrypt
		
		return null;
	}
	
	
	
	
	
}
