package com.cos.insta.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.insta.model.Follow;
import com.cos.insta.model.User;
import com.cos.insta.repository.FollowRepository;
import com.cos.insta.repository.UserRepository;
import com.cos.insta.security.MyUserDetails;

@Controller
public class FollowController {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private FollowRepository followRepo;
	

	@PostMapping("/follow/{id}")
	public @ResponseBody String follow(@AuthenticationPrincipal MyUserDetails userDetail, @PathVariable int id) {
		
		User fromUser = userDetail.getUser();
		Optional<User> oToUser = userRepo.findById(id);
		User toUser = oToUser.get();
				
		Follow follow = new Follow();
		follow.setFromUser(fromUser);
		follow.setToUser(toUser);
		
		followRepo.save(follow);
		
		return "ok"; //ResponseEntity 로 바꾸기
	}
	
	
	@DeleteMapping("/follow/{id}")
	public @ResponseBody String unFollow(@AuthenticationPrincipal MyUserDetails userDetail, @PathVariable int id) {
		User fromUser = userDetail.getUser();
		Optional<User> oToUser = userRepo.findById(id);
		User toUser = oToUser.get();
		
		followRepo.deleteByFromUserIdAndToUserId(fromUser.getId(), toUser.getId());
		
		return "ok";
	}
	
	
	// {id}를 팔로우 하고 있는 사람들
	@GetMapping("/follow/follower/{id}")
	public String followFollower(
			@PathVariable int id,
			@AuthenticationPrincipal MyUserDetails userDetail,
			Model model) {
		
		// 해당 유저의 [팔로워] 리스트
		List<Follow> followers = followRepo.findByToUserId(id);
		
		// 나의(로그인 유저) [팔로우] 리스트
		List<Follow> principalFollows = followRepo.findByFromUserId(userDetail.getUser().getId());
		
		// 해당 유저의 팔로워 리스트에 있는 사람을 내가 팔로우 하고 있는지 확인하는 과정
		for (Follow f1 : followers) {
			for (Follow f2 : principalFollows) {
				if(f1.getFromUser().getId() == f2.getToUser().getId()) {
					f1.setMatpal(true);
					break;
				}
			}
		}
				
		model.addAttribute("followers", followers);
		
		return "follow/follower";
	}

	
	// {id}가 팔로우 하는 사람들
	@GetMapping("/follow/follow/{id}")
	public String followFollow(
			@PathVariable int id,
			@AuthenticationPrincipal MyUserDetails userDetail,
			Model model) {
		
		// 해당 유저의 [팔로우] 리스트
		List<Follow> follows = followRepo.findByFromUserId(id);
		
		// 나의(로그인 유저) [팔로우] 리스트
		List<Follow> principalFollows = followRepo.findByFromUserId(userDetail.getUser().getId());
		
		// 해당 유저의 팔로우 리스트에 있는 사람을 내가 팔로우 하고 있는지 확인하는 과정
		for (Follow f1 : follows) {
			for (Follow f2 : principalFollows) {
				if(f1.getToUser().getId() == f2.getToUser().getId()) {
					f1.setMatpal(true);
					break;
				}
			}
		}
		
		model.addAttribute("follows", follows);
		
		return "follow/follow";
	}

	
}
