package com.cos.insta.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cos.insta.model.Image;
import com.cos.insta.model.Likes;
import com.cos.insta.model.Tag;
import com.cos.insta.model.User;
import com.cos.insta.repository.ImageRepository;
import com.cos.insta.repository.LikesRepository;
import com.cos.insta.repository.TagRepository;
import com.cos.insta.security.MyUserDetails;
import com.cos.insta.util.Utils;

@Controller
public class ImageController {
	
	private static final Logger log = LoggerFactory.getLogger(ImageController.class);
	
	@Value("${file.path}")
	private String fileRealPath;
	
	@Autowired
	private ImageRepository imageRepo;
	@Autowired
	private TagRepository tagRepo;
	@Autowired
	private LikesRepository likesRepo;
	
	
	@PostMapping("/image/like/{imageId}")
	public @ResponseBody String imageLike(
			@PathVariable int imageId, 
			@AuthenticationPrincipal MyUserDetails userDetail) {
		
		Likes oldLike = likesRepo.findByUserIdAndImageId(userDetail.getUser().getId(), imageId);
		
		Optional<Image> oImage = imageRepo.findById(imageId);
		Image image = oImage.get();
		
		try {
			
			if(oldLike == null) { // 좋아요 안 한 상태 (좋아요 하기)
				Likes newLike = Likes.builder()
						.user(userDetail.getUser())
						.image(image)
						.build();
				likesRepo.save(newLike);
			}else {
				likesRepo.delete(oldLike);
			}
			
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "fail";
	}
	

	@GetMapping({"/image/feed/scroll"})
	public @ResponseBody List<Image> imageFeedScroll(
			@AuthenticationPrincipal MyUserDetails userDetail, 
			@PageableDefault(size=3, sort="id", direction =  Sort.Direction.DESC) Pageable pageable, 
			Model model) {
			// 첫 페이지는 0 임
		
		log.info("username : " + userDetail.getUsername());
		
		// 1. 내가 팔로우한 사람들의 사진
		Page<Image> pageImages = imageRepo.findImages(userDetail.getUser().getId(), pageable);
		
		List<Image> images = pageImages.getContent();
		for (Image image : images) {
			Likes like = likesRepo.findByUserIdAndImageId(userDetail.getUser().getId(), image.getId());
			if(like != null) {
				image.setDoILike(true);
			}
		}
				
		return images;
	}
	
	
	@GetMapping({"/","/image/feed"})
	public String imageFeed(
			@AuthenticationPrincipal MyUserDetails userDetail, 
			@PageableDefault(size=3, sort="id", direction =  Sort.Direction.DESC) Pageable pageable, 
			Model model) {
			// 첫 페이지는 0 임
		
		log.info("username : " + userDetail.getUsername());
		
		// 1. 내가 팔로우한 사람들의 사진
		Page<Image> pageImages = imageRepo.findImages(userDetail.getUser().getId(), pageable);
		
		List<Image> images = pageImages.getContent();
		for (Image image : images) {
			Likes like = likesRepo.findByUserIdAndImageId(userDetail.getUser().getId(), image.getId());
			if(like != null) {
				image.setDoILike(true);
			}
		}
		model.addAttribute("images", images);
		
		return "image/feed";
	}
	
	
	@GetMapping("/image/upload")
	public String imageUpload() {
		
		
		return "image/image_upload";
	}
	
	
	@PostMapping("/image/uploadProc")
	public String imageUploadProc(
			@AuthenticationPrincipal MyUserDetails userDetail, 
			@RequestParam("file") MultipartFile file, 
			@RequestParam("caption") String caption, 
			@RequestParam("location") String location, 
			@RequestParam("tags") String tags ) {
		
		UUID uuid = UUID.randomUUID();
		String uuidFilename = uuid + "_"  + file.getOriginalFilename();
		
		Path filePath = Paths.get(fileRealPath+uuidFilename);
		
		try {
			Files.write(filePath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		User principal = userDetail.getUser();
		
//		Image image = Image.builder()
//				.caption(caption)
//				.location(location)
//				.user(principal)
//				.postImage(filePath.toString())
//				.build();
		
		Image image = new Image();
		image.setCaption(caption);
		image.setLocation(location);
		image.setUser(principal);
		image.setPostImage(uuidFilename);
		
		imageRepo.save(image);
		
		// Tag 객체 생성해서 넣기
		List<String> tagList = Utils.tagParser(tags);
		
		for (String tag : tagList) {
			Tag t = new Tag();
			t.setName(tag);
			t.setImage(image);
			// image객체는 영속화 되어 있는 객체이거나 혹은 DB에 존재해야함
			// 그렇지 않으면 tagRepo.save() 되지 않음
			tagRepo.save(t);
			image.getTags().add(t);
		}
		
		return "redirect:/";
	}
	
	
	@GetMapping("/image/explore")
	public String imageExplor(
			@PageableDefault(size=9, sort="id", direction =  Sort.Direction.DESC) Pageable pageable, 
			Model model) {
		
		Page<Image> pImages = imageRepo.findAll(pageable);
		List<Image> images = pImages.getContent();
		
		for (Image item : images) {
			int likeCount = likesRepo.countByImageId(item.getId());
			item.setLikeCount(likeCount);
		}
		
		
		model.addAttribute("images", images);
		
		return "image/explore";
	}
	
	
}
