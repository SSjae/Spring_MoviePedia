package com.moviepedia.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class VideoDTO {
	private int videonum;
	@NonNull
    private String moviecode;
	@NonNull
	private String videoimg;
	@NonNull
	private String videoaddr;
	@NonNull
	private String videotitle;
}
