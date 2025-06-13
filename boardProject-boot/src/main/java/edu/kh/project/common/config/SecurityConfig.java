package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
        // CORS(Cross-Origin Resource Sharing) 설정
        // 다른 도메인으로부터의 요청을 허용할지 여부를 결정함.
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // HTTP 요청에 대한 인가(Authorization) 규칙 설정
        http.authorizeHttpRequests(auth -> auth
                // "/admin/**" 경로의 요청은 인증된 사용자만 접근 가능. (예: JWT 토큰 등을 통해 인증)
                .requestMatchers("/admin/**").authenticated()
                // "/testSock/**", "/chattingSock/**" 경로의 요청은 누구나 접근 가능.
                .requestMatchers("/testSock/**", "/chattingSock/**").permitAll()
                // 위에서 정의되지 않은 그 외 모든 요청은 누구나 접근 가능.
                .anyRequest().permitAll()
        );

        // CSRF(Cross-Site Request Forgery) 보호 설정
        // CSRF 보호를 활성화하고, 특정 요청에 대해 보호를 적용하는 Matcher를 지정
        // form 기반 인증 시 CSRF 토큰을 사용하여 요청의 위변조를 방지
        http.csrf(csrf -> csrf.requireCsrfProtectionMatcher(new CsrfProtectionMatcher()));

        // X-Frame-Options 헤더 설정
        // <frame>, <iframe>, <embed>, <object> 태그 내에서 렌더링될지 여부를 제어함.
        // frameOptions.sameOrigin(): 같은 도메인 내(ex. cmh-boardproject.store)에서는 iframe 사용을 허용하지만,
        // 외부 사이트에서 우리 페이지를 iframe으로 가져가는 것은 막음.
        // 클릭재킹(Clickjacking)과 같은 공격을 방지하는 데 도움이 됨
        http.headers(headers -> headers.frameOptions(frameOptions
                -> frameOptions.sameOrigin()
        ));

        return http.build();
    }

    // Origin(도메인), HTTP 메서드, 헤더를 허용할 것인지 CORS 정책을 정의
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 자격 증명(쿠키, HTTP 인증 등) 허용 여부
        config.addAllowedOrigin("https://cmh-board-admin.vercel.app"); 
											// React 애플리케이션 도메인
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.addAllowedMethod("*"); // 모든 HTTP 메서드 (GET, POST, PUT, DELETE 등) 허용
        source.registerCorsConfiguration("/**", config); // 모든 경로에 대해 위 CORS 설정 적용
        return source;
    }

}
