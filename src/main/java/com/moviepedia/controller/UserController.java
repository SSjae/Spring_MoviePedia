package com.moviepedia.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/*")
public class UserController {
	
	@PostMapping("/login")
	public String login(String userid, String userpw, Model model) {
		String result = "";
		
		if(userid.equals("admin") && userpw.equals("admin")) {
			LocalDate now = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formatedNow = now.format(formatter);
	        
	        model.addAttribute("today", formatedNow);
			result = "user/admin";
		} else {
			result = "main";
		}
		
		return result;
	}
}
