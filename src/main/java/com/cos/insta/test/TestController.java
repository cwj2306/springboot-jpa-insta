package com.cos.insta.test;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.insta.model.Image;
import com.cos.insta.repository.ImageRepository;
import com.cos.insta.repository.UserRepository;



@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ImageRepository imageRepo;
	

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
	
	// http://localhost:8080/test/image/feed?sort=id,desc
	@GetMapping("/image/feed")
	public @ResponseBody List<Image> testImageFeed(
			@PageableDefault(size=2, sort="id", direction =  Sort.Direction.DESC) Pageable pageable){
		int userId = 3;
		Page<Image> images = imageRepo.findImages(userId, pageable);
		return images.getContent();
	}
	
	
}
