package com.moviepedia.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviepedia.domain.MovieDTO;
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
	
	// 리뷰 등록
	@PostMapping("/addReview")
	public Map<String, String> addReview(@RequestBody ReviewDTO review) {
		// 1. 먼저 리뷰 작성 및 sql 삽입
		// 2. 영화에 맞는 리뷰 평균 구하기
		// 3. 영화 moviestar update
		boolean check = rservice.addReview(review);
		double avg = rservice.reviewAvg(review.getMoviecode());
		mservice.updateStar(review.getMoviecode(), avg);
		
		MovieDTO movie = mservice.movie(review.getMoviecode());
		int rMemberCnt = rservice.rMemberCnt(review.getMoviecode());
		
		Map<String, String> result = new HashMap<String, String>();
		
		result.put("moviestar", Double.toString(movie.getMoviestar()));
		result.put("rMemberCnt", Integer.toString(rMemberCnt));
		result.put("check", Boolean.toString(check));
		
		return result;
	}
	
	// 리뷰 있는지 없는지 확인
	@GetMapping("/reviewOk")
	public ReviewDTO reviewOk(String moviecode, String useremail) {
		ReviewDTO review = rservice.review(moviecode, useremail);
		return review;
	}
}