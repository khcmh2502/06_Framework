package edu.kh.project.common.security;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.http.HttpServletRequest;

public class CsrfProtectionMatcher implements RequestMatcher {
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public boolean matches(HttpServletRequest request) {
		String path = request.getServletPath();
		// /auth/** 와 /admin/** 만 CSRF 검사 대상
		return pathMatcher.match("/auth/**", path) || pathMatcher.match("/admin/**", path);
	}
}
