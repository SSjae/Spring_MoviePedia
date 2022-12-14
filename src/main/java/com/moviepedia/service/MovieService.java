package com.moviepedia.service;

import java.util.ArrayList;

import com.moviepedia.domain.MovieDTO;

public interface MovieService {

	ArrayList<String> allGenre();

	int mtotal();

	ArrayList<MovieDTO> top10();

	ArrayList<MovieDTO> likeTop20();

	ArrayList<MovieDTO> genreMovie(String genre);

	ArrayList<MovieDTO> reMovie();

	ArrayList<MovieDTO> kMovie();

	ArrayList<MovieDTO> fMovie();

}
