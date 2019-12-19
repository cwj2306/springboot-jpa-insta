package com.cos.insta.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.client.RestTemplate;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		 RestTemplate rt = new RestTemplate();
	      
	      HttpHeaders headers = new HttpHeaders();
	      //위에서 받은 엑세스토큰
//	      headers.add("Authorization", "Bearer "+oToken.getAccess_token());
	      headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
	      
	      HttpEntity request2 = new HttpEntity(headers);
	      
//	      ResponseEntity response2 = rt.exchange(
//	            "https://kapi.kakao.com/v2/user/me",
//	            HttpMethod.POST,
//	            request,
//	            String.class);
		
		response.sendRedirect("/");
		
	}
	
}
