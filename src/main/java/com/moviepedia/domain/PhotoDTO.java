package com.moviepedia.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PhotoDTO {
	private String photocode;
	private String photoimg;
    private String moviecode;
}
