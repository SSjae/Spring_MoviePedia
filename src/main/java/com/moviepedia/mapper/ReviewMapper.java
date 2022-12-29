package com.moviepedia.mapper;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.ReviewDTO;

public interface ReviewMapper {

	int rtotal();

	int rMemberCnt(String moviecode);

	boolean addReview(ReviewDTO review);

	double reviewAvg(String moviecode);

	ReviewDTO review(@Param("moviecode")String moviecode, @Param("useremail")String useremail);
}
