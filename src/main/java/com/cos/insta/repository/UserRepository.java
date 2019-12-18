package com.cos.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.cos.insta.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUsername(String username);

	@Transactional
	@Modifying
	@Query(value = "UPDATE user SET name=?1, username=?2, website=?3, bio=?4, email=?5, phone=?6, gender=?7 WHERE id=?8", nativeQuery = true)
	void updateProfile(String name, String username, String website, String bio, String email , String phone, String gender, int id);
	
}
