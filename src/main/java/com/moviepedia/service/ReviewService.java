package com.moviepedia.service;

import com.moviepedia.domain.ReviewDTO;

public interface ReviewService {

	int rtotal();

	int rMemberCnt(String moviecode);

	boolean addReview(ReviewDTO review);

	double reviewAvg(String moviecode);

	ReviewDTO review(String moviecode, String useremail);

}