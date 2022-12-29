package com.moviepedia.service;

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
	public double reviewAvg(String moviecode) {
		return mapper.reviewAvg(moviecode);
	}
	
	@Override
	public ReviewDTO review(String moviecode, String useremail) {
		return mapper.review(moviecode, useremail);
	}
}