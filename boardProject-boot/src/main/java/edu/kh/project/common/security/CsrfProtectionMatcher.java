package edu.kh.project.common.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import jakarta.servlet.http.HttpServletRequest;

public class CsrfProtectionMatcher implements RequestMatcher{

	@Override
	public boolean matches(HttpServletRequest request) {
		String path = request.getServletPath();
        // /auth/** 와 /admin/** 만 CSRF 검사 대상
        return path.startsWith("/auth/") || path.startsWith("/admin/");
	}
}
