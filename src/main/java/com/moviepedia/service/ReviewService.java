package com.moviepedia.service;

import com.moviepedia.domain.ReviewDTO;

public interface ReviewService {

	int rtotal();

	int rMemberCnt(String moviecode);

	boolean addReview(ReviewDTO review);

	void reviewUp(String moviecode, double reviewstar);

}