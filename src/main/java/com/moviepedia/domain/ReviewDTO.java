package com.moviepedia.domain;

import lombok.Data;

@Data
public class ReviewDTO {
	private int reviewnum;
    private String useremail;
    private String moviecode;
    private String reviewcontent;
    private double reviewstar;
    private String reviewdate;
    private boolean update;
}
