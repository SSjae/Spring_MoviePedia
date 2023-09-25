package com.moviepedia.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.CommentDTO;
import com.moviepedia.domain.LikeReviewDTO;
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

	int reviewLikeCnt(int reviewnum);

	int commentCnt(int reviewnum);

	LikeReviewDTO reviewLikeOk(@Param("reviewnum")String reviewnum, @Param("useremail")String useremail);

	void addLike(@Param("reviewnum")String reviewnum, @Param("useremail")String useremail);

	void removeLike(@Param("reviewnum")String reviewnum, @Param("useremail")String useremail);

	List<CommentDTO> allComments(String reviewnum);

	void comment(CommentDTO comment);

	CommentDTO getComment(CommentDTO comment);

	boolean deleteComment(String commentnum);

	void uComment(@Param("commentnum")String commentnum, @Param("commentcontent")String commentcontent);

	ArrayList<String> reviewYear();

    ArrayList<String> reviewMonth(String yearInit);

	List<Map<String, Integer>> reviewGCnt(String graphYear);
}
