package com.cos.insta.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.insta.model.User;
import com.cos.insta.repository.FollowRepository;
import com.cos.insta.repository.UserRepository;
import com.cos.insta.security.MyUserDetails;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private FollowRepository followRepo;
	
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

		return "redirect:/auth/login";
	}
	
	@GetMapping("/auth/login")
	public String authLogin() {
		return "auth/login";
	}
	
	@GetMapping("/user/{id}")
	public String profile(
			@AuthenticationPrincipal MyUserDetails userDetail,
			@PathVariable int id,
			Model model) {

		/**
		 * 1. imageCount
		 * 2. followerCount
		 * 3. followingCoutn 
		 * 4. User 오브젝트(Image (likeCount) 컬렉션)
		 * 5. followCheck(팔로우 유무)
		 */
		
		// 4.
		Optional<User> oToUser = userRepo.findById(id);
		User toUser = oToUser.get();
		model.addAttribute("toUser", toUser);
		
		// 5.
		User user = userDetail.getUser();
		
//		int followCheck = followRepo.findByFromUserIdAndToUserId(user.getId(), id);
		int followCheck = followRepo.countByFromUserIdAndToUserId(user.getId(), id);
		model.addAttribute("followCheck", followCheck);
		
		return "user/profile";
	}
	
	
	@GetMapping("/user/edit/{id}")
	public String userEdit(@PathVariable int id) {
		
		//해당 ID로 select 하기
		// findByUserInfo() 사용 (만들어야 함)
		return "user/profile_edit";
	}
	
}
