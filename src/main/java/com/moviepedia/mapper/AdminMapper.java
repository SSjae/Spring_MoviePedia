package com.moviepedia.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;
import com.moviepedia.domain.VideoDTO;

public interface AdminMapper {
	void minsert(List<MovieDTO> movie);

	void ainsert(List<ActorDTO> actor);
	
	void pinsert(List<PhotoDTO> photo);

	void vinsert(List<VideoDTO> video);
	
	int mtotal();

	int atotal();

	int ptotal();

	int vtotal();

	void boxUpdate_0();
	
	void boxUpdate_1(@Param("boxoffice")String boxoffice, @Param("rank")int rank);

	List<String> movieCodesDB();
}