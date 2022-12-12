package com.moviepedia.mapper;

import java.util.ArrayList;

import com.moviepedia.domain.MovieDTO;

public interface MovieMapper {

	ArrayList<String> allGenre();

	int mtotal();

	ArrayList<MovieDTO> top10();
	
}
