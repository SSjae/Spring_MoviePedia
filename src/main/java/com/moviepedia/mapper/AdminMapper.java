package com.moviepedia.mapper;

import java.util.List;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;

public interface AdminMapper {
	void mdelete();
	
	void adelete();
	
	void pdelete();

	void minsert(List<MovieDTO> movie);

	void ainsert(List<ActorDTO> actor);
	
	void pinsert(List<PhotoDTO> photo);

}