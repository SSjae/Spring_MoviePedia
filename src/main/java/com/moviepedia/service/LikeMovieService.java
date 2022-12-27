package com.moviepedia.service;

import com.moviepedia.domain.LikeMovieDTO;

public interface LikeMovieService {
	LikeMovieDTO getLikeMovie(String moviecode, String useremail);

	void addLike(String moviecode, String useremail);

	void removeLike(String moviecode, String useremail);
}
