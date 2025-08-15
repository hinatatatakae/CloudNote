package com.hinata.cloudnote.controller;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hinata.cloudnote.dto.UserRegistrationDto;
import com.hinata.cloudnote.service.UserService;

@CrossOrigin(origins = "https://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {
		org.springframework.web.bind.annotation.RequestMethod.POST })
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authManager;

	public AuthController(UserService userService, AuthenticationManager authManager) {
		this.userService = userService;
		this.authManager = authManager;
	}

	/**
	 * ユーザー登録と同時に認証・セッション確立を行う
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(
			@Valid @RequestBody UserRegistrationDto dto,
			BindingResult br,
			HttpServletRequest request,
			HttpServletResponse response) {

		if (br.hasErrors()) {
			String errors = br.getFieldErrors().stream()
					.map(e -> e.getField() + ": " + e.getDefaultMessage())
					.collect(Collectors.joining("; "));
			return ResponseEntity.badRequest()
					.body(Collections.singletonMap("message", errors));
		}

		// 新規登録
		userService.register(dto);

		// 認証とセッション作成
		authenticateAndCreateSession(dto.getUsername(), dto.getPassword(), request, response);
		return ResponseEntity.ok().build();
		//return ResponseEntity.ok(newUser);
	}

	/**
	 * 既存ユーザーのログイン
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(
			@RequestBody Map<String, String> credentials,
			HttpServletRequest request,
			HttpServletResponse response) {

		String username = credentials.get("username");
		String password = credentials.get("password");

		try {
			authenticateAndCreateSession(username, password, request, response);
			return ResponseEntity.ok(Map.of(
					"message", "ログイン成功",
					"username", username));
		} catch (AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of(
							"message", "ログイン失敗: " + ex.getMessage()));
		}
	}

	/**
	 * 認証を実行し、HttpSessionを確立してSecurityContextHolderに設定
	 */
	private void authenticateAndCreateSession(
			String username,
			String password,
			HttpServletRequest request,
			HttpServletResponse response) {

		// １）認証トークン発行
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

		// ２）認証処理
		Authentication auth = authManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(auth);

		// ３）セッション生成＆有効期限設定
		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(1800); // 30分
		new HttpSessionSecurityContextRepository()
				.saveContext(SecurityContextHolder.getContext(), request, response);
	}
}
