package com.cos.insta.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cos.insta.model.User;

@Controller
@RequestMapping("/test")
public class TestController {

	@GetMapping("/home")
	public String test() {
		return "home";
	}
	
	@GetMapping("/login")
	public String testLogin() {
		return "auth/login";
	}
	
	@GetMapping("/join")
	public String testJoin() {
		return "auth/join";
	}
	
	@GetMapping("/profile")
	public String testProfile() {
		return "user/profile";
	}
	
	@GetMapping("/profile-edit")
	public String testProfileEdit() {
		return "user/profile_edit";
	}
	
	@GetMapping("/feed")
	public String testFeedt() {
		return "image/feed";
	}
	
	@GetMapping("/image-upload")
	public String testImageUpload() {
		return "image/image_upload";
	}
}
