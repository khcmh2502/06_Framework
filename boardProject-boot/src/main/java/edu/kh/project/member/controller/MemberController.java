package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("member")
@Slf4j
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	/* [로그인] 
	 * - 특정 사이터에 아이디/비밀번호 등을 입력해서
	 * 	 해당 정보가 DB에 있으면 조회/서비스 이용
	 * 
	 * - 로그인 한 회원 정보는 session에 기록하여
	 * 로그아웃 또는 브라우저 종료 시 까지
	 * 해당 정보를 계속 이용할 수 있게 함
	 * 
	 * */
	
	/** 로그인 
	 * @param inputMember : 커맨트 객체 (@ModelAttribute 생략)
	 * 						memberEmail, memberPw 세팅된 상태
	 * @return
	 */
	@PostMapping("login")
	public String login(Member inputMember) {
		
		// 로그인 서비스 호출
		Member loginMember = service.login(inputMember);
		
		
		return "redirect:/"; // 메인페이지 재요청
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
