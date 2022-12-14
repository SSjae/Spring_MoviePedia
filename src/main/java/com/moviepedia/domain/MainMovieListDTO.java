package com.moviepedia.domain;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainMovieListDTO {
	private String title;
	private ArrayList<MovieDTO> movies;
	private ArrayList<String> releases;
	private String subject;
	private boolean all;
}
