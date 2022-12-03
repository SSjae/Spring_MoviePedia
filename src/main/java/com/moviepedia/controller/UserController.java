package com.moviepedia.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moviepedia.service.UserService;

import lombok.Setter;

@Controller
@RequestMapping("/user/*")
public class UserController {
	@Setter(onMethod_ = @Autowired)
	private UserService service;
	
	@GetMapping({"/login", "/join"})
	public void replace() {
	}
	
	// 아이디 중복 체크
	@GetMapping("/check")
	@ResponseBody
	public String check(String useremail) {
		int num = service.checkEmail(useremail);
		// 중복 안됨
		String result = "OK";
		
		if(num == 1) {
			// 중복
			result = "NO";
		}
		return result;
	}
	
	// 로그인 시도
	// admin 시 admin.jsp로 현재 날짜와 Cookie에 저장된 최신화 날짜도 같이 전달
	// 일반회원시 main.jsp로 session에 로그인 정보 저장
	@PostMapping("/login")
	public String login(HttpServletRequest request, String userid, String userpw, Model model) {
		String result = "";
		
		if(userid.equals("admin") && userpw.equals("admin")) {
			LocalDate now = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formatedNow = now.format(formatter);
	        
	        String recentDate = "최신화 필요";
	        
	        Cookie[] list = request.getCookies();
			for(Cookie cookie:list) {
				if(cookie.getName().equals("recentDate")) {
					recentDate = cookie.getValue();
				}
			}
	        
	        model.addAttribute("today", formatedNow);
	        model.addAttribute("recentDate", recentDate);
			result = "user/admin";
		} else {
			result = "main";
		}
		
		return result;
	}
}
