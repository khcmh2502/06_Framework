package edu.kh.project.myPage.model.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.mapper.MyPageMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class MyPageServiceImpl implements MyPageService{
	
	@Autowired
	private MyPageMapper mapper;
	
	// Bcrypt 암호화 객체 의존성 주입(SecurityConfig 참고)
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	

	@Override
 	public int updateInfo(Member inputMember, String[] memberAddress) {
		
		// 입력된 주소가 있을 경우
		if(!inputMember.getMemberAddress().equals(",,")) {
			
			String address = String.join("^^^", memberAddress);
			inputMember.setMemberAddress(address);
			
		} else {
		// 없을 경우
			inputMember.setMemberAddress(null);
			
		}
		
		
		return mapper.updateInfo(inputMember);
	}
	
	// 비밀번호 변경 서비스
	@Override
	public int changePw(Map<String, String> paramMap, int memberNo) {
		
		// 1. 현재 비밀번호가 일치하는지 확인하기
		// - 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회
		String originPw = mapper.selectPw(memberNo);
		
		// 입력받은 현재 비밀번호와(평문)
		// DB에서 조회한 비밀번호(암호화)를 비교
		// -> bcrypt.matches(평문, 암호화비번) 사용
		
		// 다를 경우
		if( !bcrypt.matches(paramMap.get("currentPw"), originPw) ) {
			return 0;
		}
		
		// 2. 같을 경우
		
		// 새 비밀번호를 암호화 (bcrypt.encode(평문))
		String encPw = bcrypt.encode(paramMap.get("newPw"));
		
		// DB에 업데이트
		// SQL 전달 해야하는 데이터 2개 (암호화한 새 비번 encPw , 회원번호 memberNo)
		// -> mapper에 전달할수 있는 전달인자는 단 1개!
		// -> 묶어서 전달 (paramMap 재활용)
		
		paramMap.put("encPw", encPw);
		paramMap.put("memberNo", memberNo + "");
		
		return mapper.changePw(paramMap);
	}
	
	// 회원 탈퇴 서비스
	@Override
	public int secession(String memberPw, int memberNo) {
		
		// 현재 로그인한 회원의 암호화된 비밀번호 DB 조회
		String originPw = mapper.selectPw(memberNo);
		
		// 다를 경우
		if( !bcrypt.matches(memberPw, originPw) ) {
			return 0;
		}
		
		// 같은 경우
		return mapper.secession(memberNo);
	}
	
	// 파일 업로드 테스트 1
	@Override
	public String fileUpload1(MultipartFile uploadFile) throws Exception {
		
		// MultipartFile 이 제공하는 메소드
		// - getSize() : 파일 크기
		// - isEmpty() : 업로드한 파일이 없을 경우 true / 있다면 false
		// - getOriginalFileName() : 원본 파일명
		// - transferTo(경로) : 
		//	메모리 또는 임시 저장 경로에 업로드된 파일을
		//  원하는 경로에 실제로 전송(서버 어떤 경로 폴더에 저장할지 지정)
		
		// 업로드한 파일이 없을 경우
		if( uploadFile.isEmpty() ) {
			return null;
		}
		
		// 업로드한 파일이 있을 경우
		// C:/uploadFiles/test/파일명 으로 서버에 저장
		uploadFile.transferTo(new File("C:/uploadFiles/test/" + uploadFile.getOriginalFilename()));
		
		// 웹에서 해당 파일에 접근할 수 있는 경로를 반환
		// 서버 : C:/uploadFiles/test/A.jpg
		// 웹 접근 주소 : /myPage/file/A.jpg
		
		return "/myPage/file/" + uploadFile.getOriginalFilename();
	}
	
}
