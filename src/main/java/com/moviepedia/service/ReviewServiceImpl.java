package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.ReviewDTO;
import com.moviepedia.mapper.ReviewMapper;

import lombok.Setter;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Setter(onMethod_ = @Autowired)
	private ReviewMapper mapper;

	@Override
	public int rtotal() {
		return mapper.rtotal();
	}
	
	@Override
	public int rMemberCnt(String moviecode) {
		return mapper.rMemberCnt(moviecode);
	}
	
	@Override
	public boolean addReview(ReviewDTO review) {
		return mapper.addReview(review);
	}
	
	@Override
	public boolean updateReview(ReviewDTO review) {
		return mapper.updateReview(review);
	}
	
	@Override
	public boolean deleteReview(String useremail, String moviecode) {
		return mapper.deleteReview(useremail, moviecode);
	}
	
	@Override
	public String reviewAvg(String moviecode) {
		return mapper.reviewAvg(moviecode);
	}
	
	@Override
	public ReviewDTO review(String moviecode, String useremail) {
		return mapper.review(moviecode, useremail);
	}
	
	@Override
	public List<ReviewDTO> allReviews(String moviecode) {
		return mapper.allReviews(moviecode);
	}
	
	@Override
	public ReviewDTO getReview(String reviewnum) {
		return mapper.getReview(reviewnum);
	}
	
	@Override
	public ArrayList<ReviewDTO> myReview(String useremail) {
		return mapper.myReview(useremail);
	}
}