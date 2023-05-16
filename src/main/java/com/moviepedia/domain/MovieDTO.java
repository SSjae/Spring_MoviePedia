package com.moviepedia.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class MovieDTO {
	@NonNull
	private String moviecode;
	@NonNull
    private String movietitle;
	@NonNull
    private String movierelease;
	@NonNull
    private String movienation;
	@NonNull
    private String moviegenre;
	@NonNull
    private String movietime;
	@NonNull
    private String moviegrade;
	@NonNull
    private String moviedirector;
	@NonNull
    private String moviesummary;
	@NonNull
    private String movieimg;
    private double moviestar;
    private int movielike;
    private int boxoffice;
}
