package com.moviepedia.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.ReviewDTO;
import com.moviepedia.domain.UserDTO;
import com.moviepedia.service.MovieService;
import com.moviepedia.service.ReviewService;
import com.moviepedia.service.UserService;

import lombok.Setter;

@RestController
@RequestMapping("/review/*")
public class ReviewController {
	
	@Setter(onMethod_ = @Autowired)
	private ReviewService rservice;
	
	@Setter(onMethod_ = @Autowired)
	private MovieService mservice;
	
	@Setter(onMethod_ = @Autowired)
	private UserService uservice;
	
	// 리뷰 등록 및 수정
	@PostMapping("/auReview")
	public Map<String, String> auReview(@RequestBody ReviewDTO review) {
		// 1. update true:수정, false:삽입
		// 2. 먼저 리뷰 작성 및 sql 삽입/수정
		// 3. 영화에 맞는 리뷰 평균 구하기
		// 4. 영화 moviestar update
		boolean check;
		if(review.isUpdate() == true) {
			check = rservice.updateReview(review);
		} else {
			check = rservice.addReview(review);			
		}

		// avg가 하나도 없어 null로 오면 0.0으로 avg에 넣어서 update
		double avg = rservice.reviewAvg(review.getMoviecode()) == null ? 0 : Double.parseDouble(rservice.reviewAvg(review.getMoviecode()));
		mservice.updateStar(review.getMoviecode(), avg);
		
		MovieDTO movie = mservice.movie(review.getMoviecode());
		int rMemberCnt = rservice.rMemberCnt(review.getMoviecode());
		ReviewDTO recentReview = rservice.review(review.getMoviecode(), review.getUseremail());
		
		Map<String, String> result = new HashMap<String, String>();
		
		result.put("reviewnum", Integer.toString(recentReview.getReviewnum()));
		result.put("moviestar", Double.toString(movie.getMoviestar()));
		result.put("rMemberCnt", Integer.toString(rMemberCnt));
		result.put("check", Boolean.toString(check));
		
		return result;
	}
	
	// 리뷰 삭제
	@PostMapping("/deleteReview")
	public Map<String, String> deleteReview(@RequestBody Map<String, String> info) {
		// 1. 먼저 리뷰 삭제
		// 2. 영화에 맞는 리뷰 평균 구하기
		// 3. 영화 moviestar update
		String useremail = info.get("useremail");
		String moviecode = info.get("moviecode");
		
		boolean check = rservice.deleteReview(useremail, moviecode);
		
		// avg가 하나도 없어 null로 오면 0.0으로 avg에 넣어서 update
		double avg = rservice.reviewAvg(moviecode) == null ? 0 : Double.parseDouble(rservice.reviewAvg(moviecode));
		mservice.updateStar(moviecode, avg);
		
		MovieDTO movie = mservice.movie(moviecode);
		int rMemberCnt = rservice.rMemberCnt(moviecode);
		
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
		if(review == null) {
			review = new ReviewDTO();
		}
		return review;
	}
	
	// movieInfo.jsp에서 모든 코멘트 가져오기
	@GetMapping("/allReview")
	public List<Map<String, String>> allReview(String moviecode) {
		List<ReviewDTO> reviews = rservice.allReviews(moviecode);
		List<Map<String, String>> allReviews = new ArrayList<Map<String,String>>();
		for(ReviewDTO review : reviews) {
			Map<String, String> result = new HashMap<String, String>();
			result.put("reviewnum", Integer.toString(review.getReviewnum()));
			result.put("reviewcontent", review.getReviewcontent());
			result.put("reviewstar", Double.toString(review.getReviewstar()));
			result.put("username", uservice.getUser(review.getUseremail()).getUsername());
			result.put("moviecode", review.getMoviecode());
			allReviews.add(result);
		}
		
		return allReviews;
	}
	
	// 모든 리뷰 보기 사이트로 가기
	// 갈때 모든 리뷰의 리뷰넘, 컨텐트, 리뷰별점, 유저이름, 영화코드  가지고 가기
	@GetMapping(value = "/reviewAll/{moviecode}")
	public ModelAndView reviewAll(@PathVariable("moviecode")String moviecode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("review/reviewAll");
		
		List<ReviewDTO> reviews = rservice.allReviews(moviecode);
		List<Map<String, String>> allReviews = new ArrayList<Map<String,String>>();
		for(ReviewDTO review : reviews) {
			Map<String, String> result = new HashMap<String, String>();
			result.put("reviewnum", Integer.toString(review.getReviewnum()));
			result.put("reviewcontent", review.getReviewcontent());
			result.put("reviewstar", Double.toString(review.getReviewstar()));
			result.put("moviecode", moviecode);
			result.put("username", uservice.getUser(review.getUseremail()).getUsername());
			allReviews.add(result);
		}
		
		mav.addObject("allReviews", allReviews);
		return mav;
	}
	
	// 리뷰 상세정보 페이지로 가기
	// 갈때 그 리뷰DTO, 리뷰를 쓴 유저DTO, 영화 DTO, 개봉 연도만 넘기기
	@GetMapping(value="/reviewDetail/{reviewnum}/{moviecode}")
	public ModelAndView reviewDetail(@PathVariable("reviewnum")String reviewnum, @PathVariable("moviecode")String moviecode) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("review/reviewDetail");
		
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		
		ReviewDTO review = rservice.getReview(reviewnum);
		MovieDTO movie = mservice.movie(moviecode);
		UserDTO user = uservice.getUser(review.getUseremail());
		
		mav.addObject("mtotal", mtotal);
		mav.addObject("rtotal", rtotal);
		mav.addObject("review", review);
		mav.addObject("movie", movie);
		mav.addObject("user", user);
		mav.addObject("release", release(movie));
		
		return mav;
	}
	
	// 재개봉으로 인한 개봉날짜가 여러 개인 것중 맨 처음에 개봉한 연도만 뽑아내기 위한 메소드
	public String release(MovieDTO movie) {
		String result = "";
		String release = movie.getMovierelease();
		if(release.equals("")) {
			result = "";
		}
		else if(release.length() == 7) {
			// 1999 개봉
			result = release.substring(release.length()-7, release.length()-3);
		} else if (release.length() == 11) {
			// 1999 .12 개봉
			result = release.substring(release.length()-11, release.length()-7);
		} else {
			// 1999 .12.12 개봉
			result = release.substring(release.length()-14, release.length()-10);				
		}
		
		return result;
	}
}