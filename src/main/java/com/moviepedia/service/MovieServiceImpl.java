package com.moviepedia.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public int total() {
		return mapper.total();
	}
}
