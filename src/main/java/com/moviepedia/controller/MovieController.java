package com.moviepedia.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moviepedia.domain.MovieDTO;
import com.moviepedia.service.MovieService;
import com.moviepedia.service.ReviewService;

import lombok.Setter;

@Controller
@RequestMapping("/movie/*")
public class MovieController {
	@Setter(onMethod_ = @Autowired)
	private MovieService mservice;

	@Setter(onMethod_ = @Autowired)
	private ReviewService rservice;
	
	@GetMapping("/main")
	public String main(Model model) {
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		ArrayList<MovieDTO> top10 = mservice.top10();
		
		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("top10", top10);
		return "main";
	}
}