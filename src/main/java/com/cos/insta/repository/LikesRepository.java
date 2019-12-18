package com.cos.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.insta.model.Image;
import com.cos.insta.model.Likes;
import com.cos.insta.model.User;

public interface LikesRepository extends JpaRepository<Likes, Integer>{
	Likes findByUserIdAndImageId(int userId, int imageId);
	
	// 이미지 좋아요 카운트
	int countByImageId(int imageId);
}
