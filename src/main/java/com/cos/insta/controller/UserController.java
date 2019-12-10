package com.cos.insta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.insta.model.User;
import com.cos.insta.repository.UserRepository;
import com.cos.insta.security.MyUserDetails;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@GetMapping("/auth/join")
	public String authJoin() {
		return "auth/join";
	}
	
	@PostMapping("/auth/joinProc")
	public String authJoinProc(User user) {
		
		String rawPassword = user.getPassword();
		String encPassword =passwordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		userRepo.save(user);

		return "auth/login";
	}
	
	@GetMapping("/auth/login")
	public String authLogin() {
		return "auth/login";
	}
	
	
	@GetMapping("/user")
	public @ResponseBody String adminTest(@AuthenticationPrincipal MyUserDetails userDetails) {
		StringBuffer sb = new StringBuffer();
		sb.append("id : " + userDetails.getUser().getId() + "<br/>");
		sb.append("username : " + userDetails.getUsername() + "<br/>");
		sb.append("password : " + userDetails.getPassword() + "<br/>");
		sb.append("email : " + userDetails.getUser().getEmail() + "<br/>");

		return sb.toString();
	}
	
}
