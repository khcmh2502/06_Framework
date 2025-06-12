package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    new AntPathRequestMatcher("/chattingSock/**"),
                    new AntPathRequestMatcher("/testSock/**") // 웹소켓 경로 허용
                ).permitAll()
                .anyRequest().permitAll() // 나머지 경로도 우선 허용
            );
            //.csrf(csrf -> csrf.disable()); // CSRF 비활성화(웹소켓은 CSRF 보호 비활성화 필요)

        return http.build();
    }
    
	
	
}
