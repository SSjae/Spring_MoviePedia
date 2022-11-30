package com.moviepedia.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PhotoDTO {
	private int photonum;
	@NonNull
	private String photoimg;
	@NonNull
    private String moviecode;
}
