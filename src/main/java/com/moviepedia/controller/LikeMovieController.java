package com.moviepedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviepedia.domain.LikeMovieDTO;
import com.moviepedia.service.LikeMovieService;

import lombok.Setter;

@RestController
@RequestMapping("/likemovie/*")
public class LikeMovieController {
	@Setter(onMethod_ = @Autowired)
	private LikeMovieService lservice;
	
	// 보고싶어요를 했는지 안했는지 확인
	@GetMapping("/likeOk")
	public String likeOk(String moviecode, String useremail) {
		LikeMovieDTO like = lservice.getLikeMovie(moviecode, useremail);
		
		String result = "no";
		if(like != null) {
			result = "ok";
		}
		
		return result;
	}
	
	// 보고싶어요 추가 혹은 취소
	@GetMapping("/like")
	public String like(String status, String moviecode, String useremail) {
		String result = "";
		
		// status가 hate로 오면 보고싶어요 추가
		if(status.equals("hate")) {
			lservice.addLike(moviecode, useremail);
			result = "ok";
		} else {
			lservice.removeLike(moviecode, useremail);
			result = "no";
		}
		
		return result;
	}
}
