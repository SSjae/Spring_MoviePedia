package com.moviepedia.domain;

import lombok.Data;

@Data
public class ReviewDTO {
	private int reviewnum;
    private String useremail;
    private String moviecode;
    private String reviewtitle;
    private String reviewcontent;
    private int reviewstar;
}
