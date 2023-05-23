package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;
import com.moviepedia.domain.VideoDTO;
import com.moviepedia.mapper.MovieMapper;

import lombok.Setter;

@Service
public class MovieServiceImpl implements MovieService{
	@Setter(onMethod_ = @Autowired)
	private MovieMapper mapper;
	
	@Override
	public ArrayList<String> allGenre() {
		return mapper.allGenre();
	}
	
	@Override
	public int mtotal() {
		return mapper.mtotal();
	}
	
	@Override
	public ArrayList<MovieDTO> boxOffice() {
		return mapper.boxOffice();
	}
	
	@Override
	public ArrayList<MovieDTO> top10() {
		return mapper.top10();
	}
	
	@Override
	public ArrayList<MovieDTO> likeTop20() {
		return mapper.likeTop20();
	}
	
	@Override
	public ArrayList<MovieDTO> genreMovie(String genre) {
		return mapper.genreMovie(genre);
	}
	
	@Override
	public ArrayList<MovieDTO> kMovie() {
		return mapper.kMovie();
	}
	
	@Override
	public ArrayList<MovieDTO> fMovie() {
		return mapper.fMovie();
	}
	
	@Override
	public MovieDTO movie(String moviecode) {
		return mapper.movie(moviecode);
	}
	
	@Override
	public ArrayList<ActorDTO> actors(String moviecode) {
		return mapper.actors(moviecode);
	}
	
	@Override
	public ArrayList<PhotoDTO> photos(String moviecode) {
		return mapper.photos(moviecode);
	}
	
	@Override
	public ArrayList<VideoDTO> videos(String moviecode) {
		return mapper.videos(moviecode);
	}
	
	@Override
	public ArrayList<MovieDTO> similarMovie(String moviecode, String genre) {
		return mapper.similarMovie(moviecode, genre);
	}

	@Override
	public void likeup(String moviecode) {
		mapper.likeup(moviecode);
	}
	
	@Override
	public void likedown(String moviecode) {
		mapper.likedown(moviecode);
	}
	
	@Override
	public void updateStar(String moviecode, double avg) {
		mapper.updateStar(moviecode, avg);
	}
	
	@Override
	public List<Map<String, Object>> autocomplete(Map<String, Object> paramMap) {
		return mapper.autocomplete(paramMap);
	}
	
	@Override
	public ArrayList<MovieDTO> search(String keyword) {
		return mapper.search(keyword);
	}
}
