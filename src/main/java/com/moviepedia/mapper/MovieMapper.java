package com.moviepedia.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;

public interface MovieMapper {

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

	ArrayList<MovieDTO> similarMovie(@Param("moviecode")String moviecode, @Param("genre")String genre);

	void likeup(String moviecode);

	void likedown(String moviecode);
}
