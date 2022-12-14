package com.moviepedia.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.MovieDTO;
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
	public ArrayList<MovieDTO> reMovie() {
		return mapper.reMovie();
	}
	
	@Override
	public ArrayList<MovieDTO> kMovie() {
		return mapper.kMovie();
	}
	
	@Override
	public ArrayList<MovieDTO> fMovie() {
		return mapper.fMovie();
	}
	
	
}
