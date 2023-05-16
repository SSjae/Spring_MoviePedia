package com.moviepedia.mapper;

import java.util.List;

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
}