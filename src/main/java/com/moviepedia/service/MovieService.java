package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;
import com.moviepedia.domain.VideoDTO;

public interface MovieService {

	ArrayList<String> allGenre();

	int mtotal();

	ArrayList<MovieDTO> boxOffice();

	ArrayList<MovieDTO> top10();

	ArrayList<MovieDTO> likeTop20();

	ArrayList<MovieDTO> genreMovie(String genre);

	ArrayList<MovieDTO> kMovie();

	ArrayList<MovieDTO> fMovie();

	MovieDTO movie(String moviecode);

	ArrayList<ActorDTO> actors(String moviecode);

	ArrayList<PhotoDTO> photos(String moviecode);

	ArrayList<VideoDTO> videos(String moviecode);

	ArrayList<MovieDTO> similarMovie(String moviecode, String genre);

	void likeup(String moviecode);

	void likedown(String moviecode);

	void updateStar(String moviecode, double avg);

	List<Map<String, Object>> autocomplete(Map<String, Object> paramMap);

	ArrayList<MovieDTO> search(String keyword);
}
