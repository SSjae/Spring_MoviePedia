package com.moviepedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviepedia.domain.ReviewDTO;
import com.moviepedia.service.MovieService;
import com.moviepedia.service.ReviewService;

import lombok.Setter;

@RestController
@RequestMapping("/review/*")
public class ReviewController {
	
	@Setter(onMethod_ = @Autowired)
	private ReviewService rservice;
	
	@Setter(onMethod_ = @Autowired)
	private MovieService mservice;
	
	@PostMapping("/addReview")
	public String addReview(@RequestBody ReviewDTO review) {
		boolean check = rservice.addReview(review);
		rservice.reviewUp(review.getMoviecode(), review.getReviewstar());
		
		String result = "no";
		
		if(check) {
			result = "ok";
		}
		
		return result;
	}
}