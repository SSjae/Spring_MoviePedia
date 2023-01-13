package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;

import com.moviepedia.domain.ReviewDTO;

public interface ReviewService {

	int rtotal();

	int rMemberCnt(String moviecode);

	boolean addReview(ReviewDTO review);

	boolean updateReview(ReviewDTO review);

	boolean deleteReview(String useremail, String moviecode);

	String reviewAvg(String moviecode);

	ReviewDTO review(String moviecode, String useremail);

	List<ReviewDTO> allReviews(String moviecode);

	ReviewDTO getReview(String reviewnum);

	ArrayList<ReviewDTO> myReview(String useremail);

}