package com.cos.insta.test;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.insta.model.User;
import com.cos.insta.repository.UserRepository;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UserRepository userRepo;

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
	
	@GetMapping("/user/{id}")
	public @ResponseBody User testUser(@PathVariable int id) {
		
		Optional<User> oUser = userRepo.findById(id);
		User user = oUser.get();
		
		return user;
	}
	
}
