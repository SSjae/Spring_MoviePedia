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
    private String movieKtitle;
	@NonNull
    private String movieEtitle;
	@NonNull
    private String moviegenre;
	@NonNull
    private String movienation;
	@NonNull
    private String movietime;
	@NonNull
    private String movierelease;
	@NonNull
    private String moviedirector;
	@NonNull
    private String moviegrade;
	@NonNull
    private String movieimg;
	@NonNull
    private String movieHsummary;
	@NonNull
    private String movieCsummary;
    private double moviestar;
    private int movielike;
}
