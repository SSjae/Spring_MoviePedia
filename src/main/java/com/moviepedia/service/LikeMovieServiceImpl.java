package com.moviepedia.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.LikeMovieCntDTO;
import com.moviepedia.domain.LikeMovieDTO;
import com.moviepedia.mapper.LikeMovieMapper;

import lombok.Setter;

@Service
public class LikeMovieServiceImpl implements LikeMovieService {
	@Setter(onMethod_ = @Autowired)
	private LikeMovieMapper mapper;
	
	@Override
	public LikeMovieDTO getLikeMovie(String moviecode, String useremail) {
		return mapper.getLikeMovie(moviecode, useremail);
	}
	
	@Override
	public void addLike(String moviecode, String useremail) {
		mapper.addLike(moviecode, useremail);
	}
	
	@Override
	public void removeLike(String moviecode, String useremail) {
		mapper.removeLike(moviecode, useremail);	
	}
	
	@Override
	public ArrayList<LikeMovieDTO> myLike(String useremail) {
		return mapper.myLike(useremail);
	}
	
	@Override
	public ArrayList<LikeMovieCntDTO> lCnt(String moviecode) {
		return mapper.lCnt(moviecode);
	}
}
