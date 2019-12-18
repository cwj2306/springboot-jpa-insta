package com.cos.insta.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username; // String 은 default 256Byte
	@JsonIgnore
	private String password;
	private String name; // 실명
	private String website; // 개인 홈페이지 주소
	private String bio; // 자기소개
	private String email;
	private String phone;
	private String gender;
	private String profileImage; //프로필 이미지 경로+이름
	
	// findById() 할 때는 들고옴
	// 유저가 작성한 이미지 필요없이 유저정보만 필요한 경우에는 네이티브 쿼리 따로 작성해서 사용 할 것
	@OneToMany(mappedBy = "user") //DB에 들어가지 않음
	@JsonIgnoreProperties({"user", "tage", "likes"})
	@OrderBy("id desc")
	private List<Image> images = new ArrayList<>();
	
	@CreationTimestamp // null 일 경우 자동으로 현재시간 들어감
	private Timestamp createDate;
	@CreationTimestamp
	private Timestamp updateDate;
	
	private String provider; // kakao or google or facebook ...
	private String providerId;
}
