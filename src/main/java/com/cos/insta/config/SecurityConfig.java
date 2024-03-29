package com.cos.insta.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.insta.handler.MyLogoutSuccessHandler;


@Configuration
@EnableWebSecurity //스프링 시큐리티 필터에 등록하는 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 1. Bean 어노테이션은 메서드에 붙여서 객체 생성시 사용
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 2. 필터링
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors().disable();
		
		http.authorizeRequests()
		.antMatchers("/", "/user/**", "/follow/**", "/image/**") //잠김
		.authenticated()
		.anyRequest().permitAll() // 이외의 모든 주소는 다 접근 가능
		.and()
		.formLogin()
		.loginPage("/auth/login") //기본 제공하는 로그인 페이지 대체
		.loginProcessingUrl("/auth/loginProc")
		.defaultSuccessUrl("/");
//		.and()
//		.logout()
		//로그아웃에 성공했을 때
		//이 것은 핸들러를 사용한 것이고
		//로그인처럼 url을 사용해도된다.
		//.logoutSuccessUrl("/home")
//		.logoutSuccessHandler(new MyLogoutSuccessHandler());



	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	// 내가 인코딩하는게 아니라, 어떤 인코딩으로 패스워드가 만들어졌는지 알려주는 거야!!
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
	}



}