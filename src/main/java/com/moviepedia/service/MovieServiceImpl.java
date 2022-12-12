package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;

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
}
