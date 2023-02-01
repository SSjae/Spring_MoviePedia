package com.moviepedia.domain;

import lombok.Data;

@Data
public class LikeReviewDTO {
	private int likereviewnum;
	private int reviewnum;
	private String useremail;
}
