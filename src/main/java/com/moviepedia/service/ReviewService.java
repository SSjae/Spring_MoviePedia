package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;

import com.moviepedia.domain.CommentDTO;
import com.moviepedia.domain.LikeReviewDTO;
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

	int reviewLikeCnt(int reviewnum);

	int commentCnt(int reviewnum);

	LikeReviewDTO reviewLikeOk(String reviewnum, String useremail);

	void addLike(String reviewnum, String useremail);

	void removeLike(String reviewnum, String useremail);

	List<CommentDTO> allComments(String reviewnum);

	void comment(CommentDTO comment);

	CommentDTO getComment(CommentDTO comment);

	boolean deleteComment(String commentnum);

	void uComment(String commentnum, String commentcontent);

	ArrayList<String> reviewYear();
}