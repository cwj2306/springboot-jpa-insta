package com.cos.insta.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.insta.model.User;

// Principal(접근주체) = 세션처럼 사용 = Spring Security Context 에 보관됨
public class MyUserDetails implements UserDetails{

	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	//계정의 패스워드를 리턴한다
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	//계정의 패스워드를 리턴한다
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지를 리턴한다(true를 리턴하면 만료되지 않음을 의미)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있지 않은지를 리턴한다(true를 리턴하면 계정이 잠겨있지 않음을 의미)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//계정의 패스워드가 만료되지 않았는지를 리턴한다(true를 리턴하면 패스워드가 만료되지 않음을 의미)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 사용가능한 계정인지를 리턴한다(true를 리턴하면 사용가능한 계정인지를 의미)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//계정이 갖고 있는 권한 목록을 리턴한다
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
