package com.cos.insta.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String location; // 사진 찍은 장소
	private String caption; // 사진 설명
	private String postImage; // 포스팅 사진 이름
	
	@ManyToOne // 실제 DB에는 FK로 들어감 
	@JoinColumn(name = "userId") // FK 기본 컬럼명이 user_id 이므로 userId로 명시해줌
	@JsonIgnoreProperties({"password", "images"}) // jackson 라이브러리가 객체를 json으로 바꿀 때 password 필드 무시함
	// controller 내부에서는 사용 할 수 있음, return 으로 내보낼 때(json으로 변환해서 내보냄) 무시
	private User user;
	
	// Tag List
//	@OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "image")
	@JsonManagedReference
	private List<Tag> tags = new ArrayList<>();
	
	// Like List
	@OneToMany(mappedBy = "image")
	private List<Likes> likes = new ArrayList<>();
	
	@Transient // DB에 영향을 미치지 않음
	private int likeCount;
	
	@Transient // DB에 영향을 미치지 않음
	private boolean doILike;
	
	@CreationTimestamp
	private Timestamp createDate;
	@CreationTimestamp
	private Timestamp updateDate;
}
