package com.moviepedia.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.ReviewDTO;

public interface ReviewMapper {

	int rtotal();

	int rMemberCnt(String moviecode);

	boolean addReview(ReviewDTO review);

	boolean updateReview(ReviewDTO review);

	boolean deleteReview(@Param("useremail")String useremail, @Param("moviecode")String moviecode);

	String reviewAvg(String moviecode);

	ReviewDTO review(@Param("moviecode")String moviecode, @Param("useremail")String useremail);

	List<ReviewDTO> allReviews(String moviecode);

	ReviewDTO getReview(String reviewnum);

	ArrayList<ReviewDTO> myReview(String useremail);

}
