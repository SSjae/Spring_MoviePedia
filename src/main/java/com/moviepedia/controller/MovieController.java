package com.moviepedia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie/*")
public class MovieController {
	
	@GetMapping("/main")
	public String main() {
		return "main";
	}
}
