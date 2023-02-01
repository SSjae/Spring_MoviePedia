package com.moviepedia.domain;

import lombok.Data;

@Data
public class CommentDTO {
	private int commentnum;
    private int reviewnum;
    private String useremail;
    private String commentcontent;
    private String commentdate;
}
