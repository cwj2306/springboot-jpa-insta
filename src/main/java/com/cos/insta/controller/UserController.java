package com.cos.insta.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cos.insta.model.Image;
import com.cos.insta.model.User;
import com.cos.insta.repository.FollowRepository;
import com.cos.insta.repository.LikesRepository;
import com.cos.insta.repository.UserRepository;
import com.cos.insta.security.MyUserDetails;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private FollowRepository followRepo;
	@Autowired
	private LikesRepository likesRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Value("${file.path}")
	private String fileRealPath;

	
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
		
		
		Optional<User> oToUser = userRepo.findById(id);
		User toUser = oToUser.get();
		
		// 1.
		int imageCount = toUser.getImages().size();
		model.addAttribute("imageCount", imageCount);
		
		// 2. (select count(*) from follow where fromUserId = 1)
		int followCount = followRepo.countByFromUserId(toUser.getId());
		model.addAttribute("followCount", followCount);
		
		// 3. (select count(*) from follow where toUserId = 1)
		int followerCount = followRepo.countByToUserId(toUser.getId());
		model.addAttribute("followerCount", followerCount);
		
		// 4. 
		for (Image item : toUser.getImages()) {
			int likeCount = likesRepo.countByImageId(item.getId());
			item.setLikeCount(likeCount);
		}
		
		model.addAttribute("toUser", toUser);
		
		// 5.
		User principal = userDetail.getUser();
		
//		int followCheck = followRepo.findByFromUserIdAndToUserId(user.getId(), id);
		int followCheck = followRepo.countByFromUserIdAndToUserId(principal.getId(), id);
		model.addAttribute("followCheck", followCheck);
		
		return "user/profile";
	}
	
	
	@PostMapping("/user/profileUpload")
	public String userProfileUpload(
			@RequestParam("profileImage") MultipartFile file, 
			@AuthenticationPrincipal MyUserDetails userDetail) throws IOException {
		User principal = userDetail.getUser();
		
		//파일 처리
		UUID uuid = UUID.randomUUID();
		String uuidFilename = uuid + "_" + file.getOriginalFilename();
		Path filePath = Paths.get(fileRealPath+uuidFilename);
		Files.write(filePath, file.getBytes());
		
		principal.setProfileImage(uuidFilename);
		userRepo.save(principal);
		
		return "redirect:/user/"+principal.getId();
	}
	
	
	@GetMapping("/user/edit")
	public String userEdit() {
		
		return "user/profile_edit";
	}
	
	@PutMapping("/user/editProc")
	public String userEditProc(
			@AuthenticationPrincipal MyUserDetails userDetail,
			User user) {
		
		userRepo.updateProfile(
				user.getName(), 
				user.getUsername(), 
				user.getWebsite(), 
				user.getBio(), 
				user.getEmail(), 
				user.getPhone(),
				user.getGender(), 
				userDetail.getUser().getId()
		);
		
		return "redirect:/user/"+userDetail.getUser().getId();
	}
	
}
