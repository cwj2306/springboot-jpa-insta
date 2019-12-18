package com.cos.insta.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.insta.model.Follow;

public interface FollowRepository extends JpaRepository<Follow, Integer>{

	//unFollow
	@Transactional
	int  deleteByFromUserIdAndToUserId(int fromUserId, int toUserId);
	
	//팔로우 유무 확인
//	@Query(value = "SELECT count(*) FROM follow WHERE fromUserId = ?1 AND toUserId = ?2", nativeQuery = true)
//	int  findByFromUserIdAndToUserId(int fromUserId, int toUserId);
	int  countByFromUserIdAndToUserId(int fromUserId, int toUserId);
	
	// 팔로우 리스트
	List<Follow> findByFromUserId(int fromUserId);
	
	// 팔로워 리스트
	List<Follow> findByToUserId(int toUserId);

	// 팔로우
	int countByFromUserId(int fromUserId);
	
	// 팔로워
	int countByToUserId(int toUserId);
	
}
