package com.moviepedia.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ActorDTO {
	private int actornum;
	@NonNull
	private String actorcode;
	@NonNull
    private String moviecode;
	@NonNull
    private String actorname;
	@NonNull
    private String actorpart;
	@NonNull
    private String actorrole;
	@NonNull
    private String actorimg;
}
