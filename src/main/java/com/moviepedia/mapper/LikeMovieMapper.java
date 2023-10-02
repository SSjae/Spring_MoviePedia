package com.moviepedia.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.LikeMovieDTO;

public interface LikeMovieMapper {
	LikeMovieDTO getLikeMovie(@Param("moviecode")String moviecode, @Param("useremail")String useremail);

	void addLike(@Param("moviecode")String moviecode, @Param("useremail")String useremail);

	void removeLike(@Param("moviecode")String moviecode, @Param("useremail")String useremail);

	ArrayList<LikeMovieDTO> myLike(String useremail);
}
