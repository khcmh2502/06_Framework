package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import edu.kh.project.common.security.CsrfProtectionMatcher;

/*
 * @Configuration
 * - 해당 클래스가 설정용 클래스임을 명시
 * + 객체로 생성해서 내부 코드를 서버 실행시 모두 수행
 * 
 * @Bean 
 * - 개발자가 수동으로 생성한 객체의 관리를
 *   스프링에게 넘기는 어노테이션(Bean 등록)
 * 
 * */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> 
			auth.requestMatchers("/admin/**").authenticated() // JWT 토큰 방식 이용시 참고
				.requestMatchers("/testSock/**","/chattingSock/**").permitAll() // 누구나 접근 가능
				.anyRequest().permitAll()) // 그 외 모든 요청도 permitAll
				.csrf(csrf -> csrf
				    .requireCsrfProtectionMatcher(new CsrfProtectionMatcher())
				    // /auth/** 와 /admin/** 제외한 나머지 제외 (CSRF 검사 X)
				);

		return http.build();
	}

}
