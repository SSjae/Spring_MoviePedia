package com.moviepedia.service;

import java.util.ArrayList;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;

public interface MovieService {

	ArrayList<String> allGenre();

	int mtotal();

	ArrayList<MovieDTO> top10();

	ArrayList<MovieDTO> likeTop20();

	ArrayList<MovieDTO> genreMovie(String genre);

	ArrayList<MovieDTO> kMovie();

	ArrayList<MovieDTO> fMovie();

	MovieDTO movie(String moviecode);

	ArrayList<ActorDTO> actors(String moviecode);

	ArrayList<PhotoDTO> photos(String moviecode);

}
