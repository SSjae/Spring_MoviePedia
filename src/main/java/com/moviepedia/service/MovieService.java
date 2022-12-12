package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;

import com.moviepedia.domain.MovieDTO;

public interface MovieService {

	ArrayList<String> allGenre();

	int mtotal();

	ArrayList<MovieDTO> top10();

}
