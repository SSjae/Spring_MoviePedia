package com.moviepedia.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moviepedia.service.AdminService;

import lombok.Setter;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	@Setter(onMethod_ = @Autowired)
	private AdminService service;
	
	// 현재 날짜 메소드
	public String nowDate() {
		LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedNow = now.format(formatter);
        
        return formatedNow;
	}
	
	// admin 페이지로 가기
	// 오늘날짜, 쿠키에 저장된 최신화 날짜 전달
	@GetMapping("/page")
	public String admin(@CookieValue String recentDate, Model model) {
        model.addAttribute("today", nowDate());
        model.addAttribute("recentDate", recentDate);
		return "user/admin";
	}
	
	// 영화 최신화 하기
	// alert 띄우기 위해 alert.jsp로
	//최신화 날짜 Cookie 생성
	@GetMapping("/recent")
	public String recent(HttpServletResponse response, Model model) {
		service.recent();
		
		Cookie cookie = new Cookie("recentDate", nowDate());
		cookie.setDomain("localhost");
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24*30*12);
		response.addCookie(cookie);
		
		int mtotal = service.mtotal();
		int atotal = service.atotal();
		int ptotal = service.ptotal();
		
		model.addAttribute("msg", "영화 "+mtotal+"개, 출연자 "+atotal+"명, 포토 "+ptotal+"개 최신화가 완료되었습니다.");
		model.addAttribute("url", "admin/page");
		return "alert";
	}
}