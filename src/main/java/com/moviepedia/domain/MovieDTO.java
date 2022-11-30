package com.moviepedia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDTO {
	private String moviecode;
    private String movieKtitle;
    private String movieEtitle;
    private String moviegenre;
    private String movienation;
    private String movietime;
    private String movierelease;
    private String moviedirector;
    private String moviegrade;
    private String movieimg;
    private String movieHsummary;
    private String movieCsummary;
}
