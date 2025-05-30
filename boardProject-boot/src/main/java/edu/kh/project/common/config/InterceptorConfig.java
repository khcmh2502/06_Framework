package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.project.common.interceptor.BoardTypeInterceptor;

// 인터셉터가 어떤 요청을 가로챌지 설정하는 클래스

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	// 인터셉터 클래스 Bean 등록
	@Bean 
	public BoardTypeInterceptor boardTypeInterceptor() {
		return new BoardTypeInterceptor();
	}

	// 동작할 인터셉터 객체를 추가하는 메서드
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// Bean으로 등록된 BoardTypeInterceptor를 얻어와서 매개변수로 전달
		registry
		.addInterceptor(boardTypeInterceptor())
		.addPathPatterns("/**") // 가로챌 요청 주소를 지정  /** : / 이하 모든 요청 주소
		.excludePathPatterns("/css/**", 
							"/js/**", 
							"/images/**", 
							"/favicon.ico"); // 가로채지 않을 주소를 지정
					
	
		WebMvcConfigurer.super.addInterceptors(registry); // WebMvcConfigurer 인터페이스의 기본 구현(디폴트 메서드)를 호출
		// 내가 WebMvcConfigurer를 구현한 클래스이고, 부모 인터페이스의 기본 로직도 같이 실행하고 싶을 때
		// 없어도됨(오히려 없는게 가독성에 좋음)
	}
}
