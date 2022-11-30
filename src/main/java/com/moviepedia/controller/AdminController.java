package com.moviepedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moviepedia.service.AdminService;

import lombok.Setter;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	@Setter(onMethod_ = @Autowired)
	private AdminService service;
	
	@GetMapping("/recent")
	public String recent() {
		service.recent();
		
		return "user/admin";
	}
}
