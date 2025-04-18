package edu.kh.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller  // 요청/응답 제어하는 역할 명시 + Bean 등록
@RequestMapping("ajax")  // 요청주소 시작이 "ajax"인 요청을 매핑
@Slf4j
public class AjaxController {
	
	@GetMapping("main")
	public String ajaxMain() {
		return "ajax/main";
	}
	
}
