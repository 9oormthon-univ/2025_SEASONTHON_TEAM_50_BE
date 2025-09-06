package com.ready.weddingplanner.controller;

import com.ready.weddingplanner.domain.User;
import com.ready.weddingplanner.dto.AuthDto;
import com.ready.weddingplanner.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthDto.UserResponse> signup(@RequestBody AuthDto.SignUpRequest request) {
		User newUser = authService.signup(request);
		return new ResponseEntity<>(new AuthDto.UserResponse(newUser), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthDto.UserResponse> login(@RequestBody AuthDto.LoginRequest request,
		HttpServletRequest httpServletRequest) {
		User user = authService.login(request, httpServletRequest);
		return ResponseEntity.ok(new AuthDto.UserResponse(user));
	}

	@PostMapping("/dev-login")
	public ResponseEntity<AuthDto.UserResponse> devLogin(@RequestParam String email) {
		User user = authService.devLogin(email);
		return ResponseEntity.ok(new AuthDto.UserResponse(user));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		SecurityContextHolder.clearContext();
		return ResponseEntity.noContent().build();
	}
}
